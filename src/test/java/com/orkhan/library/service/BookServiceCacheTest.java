package com.orkhan.library.service;

import com.orkhan.library.dto.BookRequestDto;
import com.orkhan.library.dto.BookResponseDto;
import com.orkhan.library.entity.Author;
import com.orkhan.library.entity.Book;
import com.orkhan.library.repository.AuthorRepository;
import com.orkhan.library.repository.BookRepository;
import com.orkhan.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"jwt.secret=test-secret-for-tests-only", "JWT_SECRET=test-secret-for-tests-only"})
@Transactional
class BookServiceCacheTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void clearBookCache() {
        Cache cache = cacheManager.getCache("books");
        if (cache != null) {
            cache.clear();
        }
    }

    @Test
    void shouldRemoveCachedBookAfterUpdate() {
        Author author = new Author();
        author.setFullName("Test Author");
        author.setNationality("USA");
        author = authorRepository.save(author);

        Book book = new Book();
        book.setTitle("Original Title");
        book.setIsbn("CACHE-TEST-123");
        book.setPublicationYear(2025);
        book.setAuthor(author);

        book = bookRepository.save(book);

        Long bookId = book.getId();

        BookResponseDto firstRead = bookService.getBookById(bookId);

        assertEquals("Original Title", firstRead.getTitle());

        Cache cache = cacheManager.getCache("books");

        assertNotNull(cache);
        assertNotNull(cache.get(bookId));

        BookRequestDto updateRequest = new BookRequestDto();
        updateRequest.setTitle("Updated Title");
        updateRequest.setIsbn("Mozambique-Madagascar-005");
        updateRequest.setPublicationYear(2026);
        updateRequest.setAuthorId(author.getId());

        bookService.updateBook(bookId, updateRequest);

        assertNull(cache.get(bookId));

        BookResponseDto updatedBook = bookService.getBookById(bookId);

        assertEquals("Updated Title", updatedBook.getTitle());
        assertNotNull(cache.get(bookId));
    }
}