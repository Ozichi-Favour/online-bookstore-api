package com.example.online.bookstore.controller;


import com.example.online.bookstore.enums.Genre;
import com.example.online.bookstore.model.Book;
import com.example.online.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

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
    void searchBooks_WithTitle_ShouldReturnMatchingBooks() throws Exception {
        when(bookService.searchByTitle("Test"))
                .thenReturn(Arrays.asList(testBook));

        mockMvc.perform(get("/api/books/search")
                        .param("title", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"))
                .andExpect(jsonPath("$[0].author").value("Test Author"));
    }

    @Test
    void getBook_ShouldReturnBook_WhenExists() throws Exception {
        when(bookService.getBookById(1L))
                .thenReturn(testBook);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"));
    }
}