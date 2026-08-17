package com.orkhan.library.service.impl;

import com.orkhan.library.dto.BookRequestDto;
import com.orkhan.library.dto.BookResponseDto;
import com.orkhan.library.entity.Book;
import com.orkhan.library.repository.BookRepository;
import com.orkhan.library.repository.AuthorRepository;
import com.orkhan.library.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.orkhan.library.specification.BookSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import java.util.NoSuchElementException;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<BookResponseDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::convertToResponseDto);
    }

    @Override
    public BookResponseDto saveBook(BookRequestDto request) {
        Book book = new Book();

        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());

        book.setAuthor(authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new NoSuchElementException("Author not found")));

        Book savedBook = bookRepository.save(book);

        return convertToResponseDto(savedBook);
    }

    @Override
    @CacheEvict(value = "books", key = "#id")
    public BookResponseDto updateBook(Long id, BookRequestDto request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));

        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setAuthor(authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new NoSuchElementException("Author not found")));

        Book updatedBook = bookRepository.save(book);

        return convertToResponseDto(updatedBook);
    }

    @Override
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));
        bookRepository.delete(book);
    }

    @Override
    public Page<BookResponseDto> searchBooks(String title, Integer year, Pageable pageable) {
        Specification<Book> specification = Specification.where(BookSpecification.hasTitle(title)).and(BookSpecification.hasPublicationYear(year));
        return bookRepository.findAll(specification, pageable).map(this::convertToResponseDto);
    }

    @Override
    @Cacheable(value = "books", key = "#id")
    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));
        return convertToResponseDto(book);
    }

    private BookResponseDto convertToResponseDto(Book book) {
        BookResponseDto dto = new BookResponseDto();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPublicationYear(book.getPublicationYear());

        dto.setAuthorId(book.getAuthor().getId());
        dto.setAuthorName(book.getAuthor().getFullName());

        return dto;
    }
}