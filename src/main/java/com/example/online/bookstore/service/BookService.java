package com.example.online.bookstore.service;

import com.example.online.bookstore.enums.Genre;
import com.example.online.bookstore.model.Book;

import java.util.List;

public interface BookService {
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    List<Book> searchByYear(Integer year);
    List<Book> searchByGenre(Genre genre);
    Book getBookById(Long id);
}