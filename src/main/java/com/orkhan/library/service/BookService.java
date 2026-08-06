package com.orkhan.library.service;

import com.orkhan.library.dto.BookRequestDto;
import com.orkhan.library.dto.BookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Page<BookResponseDto> getAllBooks(Pageable pageable);

    Page<BookResponseDto> searchBooks(String title, Integer year, Pageable pageable);

    BookResponseDto getBookById(Long id);

    BookResponseDto saveBook(BookRequestDto request);

    BookResponseDto updateBook(Long id, BookRequestDto request);

    void deleteBook(Long id);
}