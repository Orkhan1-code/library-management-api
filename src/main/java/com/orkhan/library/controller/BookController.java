package com.orkhan.library.controller;

import com.orkhan.library.service.BookService;
import com.orkhan.library.dto.BookRequestDto;
import com.orkhan.library.dto.BookResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Page<BookResponseDto> getAllBooks(Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }

    @PostMapping
    public BookResponseDto saveBook(@Valid @RequestBody BookRequestDto request) {
        return bookService.saveBook(request);
    }

    @GetMapping("/{id}")
    public BookResponseDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public BookResponseDto updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDto request) {
        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/search")
    public Page<BookResponseDto> searchBook(@RequestParam(required = false) String title, @RequestParam(required = false) Integer year, Pageable pageable) {
        return bookService.searchBooks(title, year, pageable);
    }
}