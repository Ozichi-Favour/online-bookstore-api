package com.example.online.bookstore.service.impl;


import com.example.online.bookstore.enums.Genre;
import com.example.online.bookstore.model.Book;
import com.example.online.bookstore.repository.BookRepository;
import com.example.online.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    @Override
    public List<Book> searchByYear(Integer year) {
        return bookRepository.findByPublicationYear(year);
    }

    @Override
    public List<Book> searchByGenre(Genre genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
