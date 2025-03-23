package com.example.online.bookstore.service;


import com.example.online.bookstore.enums.Genre;
import com.example.online.bookstore.model.Book;
import com.example.online.bookstore.repository.BookRepository;
import com.example.online.bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setGenre(Genre.FICTION);
        testBook.setIsbn("978-1234567890");
        testBook.setPublicationYear(2022);
        testBook.setPrice(29.99);
    }

    @Test
    void searchByTitle_ShouldReturnMatchingBooks() {
        when(bookRepository.findByTitleContainingIgnoreCase("Test"))
                .thenReturn(Arrays.asList(testBook));

        List<Book> result = bookService.searchByTitle("Test");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
    }

    @Test
    void searchByAuthor_ShouldReturnMatchingBooks() {
        when(bookRepository.findByAuthorContainingIgnoreCase("Test"))
                .thenReturn(Arrays.asList(testBook));

        List<Book> result = bookService.searchByAuthor("Test");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Author", result.get(0).getAuthor());
    }

    @Test
    void getBookById_ShouldReturnBook_WhenExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        Book result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getBookById_ShouldThrowException_WhenNotExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.getBookById(1L));
    }
}