package com.example.online.bookstore.repository;

import com.example.online.bookstore.enums.Genre;
import com.example.online.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByPublicationYear(Integer year);
    List<Book> findByGenre(Genre genre);
}