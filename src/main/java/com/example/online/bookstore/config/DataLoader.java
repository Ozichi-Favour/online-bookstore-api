package com.example.online.bookstore.config;



import com.example.online.bookstore.enums.Genre;
import com.example.online.bookstore.model.Book;
import com.example.online.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) {
        // Only load data if the repository is empty
        if (bookRepository.count() == 0) {
            loadSampleBooks();
        }
    }

    private void loadSampleBooks() {
        Book book1 = new Book();
        book1.setTitle("The Great Adventure");
        book1.setAuthor("John Smith");
        book1.setGenre(Genre.FICTION);
        book1.setIsbn("978-1234567890");
        book1.setPublicationYear(2020);
        book1.setPrice(29.99);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Mystery at Midnight");
        book2.setAuthor("Sarah Johnson");
        book2.setGenre(Genre.MYSTERY);
        book2.setIsbn("978-0987654321");
        book2.setPublicationYear(2021);
        book2.setPrice(24.99);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("Dark Shadows");
        book3.setAuthor("Michael Brown");
        book3.setGenre(Genre.HORROR);
        book3.setIsbn("978-5678901234");
        book3.setPublicationYear(2019);
        book3.setPrice(19.99);
        bookRepository.save(book3);

        Book book4 = new Book();
        book4.setTitle("Laughing Out Loud");
        book4.setAuthor("Emma Wilson");
        book4.setGenre(Genre.SATIRE);
        book4.setIsbn("978-4321098765");
        book4.setPublicationYear(2022);
        book4.setPrice(22.99);
        bookRepository.save(book4);

        Book book5 = new Book();
        book5.setTitle("Verses of Love");
        book5.setAuthor("David Lee");
        book5.setGenre(Genre.POETRY);
        book5.setIsbn("978-8901234567");
        book5.setPublicationYear(2021);
        book5.setPrice(15.99);
        bookRepository.save(book5);

        Book book6 = new Book();
        book6.setTitle("The Silent Witness");
        book6.setAuthor("Patricia White");
        book6.setGenre(Genre.THRILLER);
        book6.setIsbn("978-3456789012");
        book6.setPublicationYear(2020);
        book6.setPrice(27.99);
        bookRepository.save(book6);

        Book book7 = new Book();
        book7.setTitle("Beyond the Stars");
        book7.setAuthor("Robert Black");
        book7.setGenre(Genre.FICTION);
        book7.setIsbn("978-6789012345");
        book7.setPublicationYear(2022);
        book7.setPrice(25.99);
        bookRepository.save(book7);

        Book book8 = new Book();
        book8.setTitle("The Last Case");
        book8.setAuthor("Mary Thompson");
        book8.setGenre(Genre.MYSTERY);
        book8.setIsbn("978-9012345678");
        book8.setPublicationYear(2021);
        book8.setPrice(23.99);
        bookRepository.save(book8);

        Book book9 = new Book();
        book9.setTitle("Midnight Tales");
        book9.setAuthor("James Wilson");
        book9.setGenre(Genre.HORROR);
        book9.setIsbn("978-2345678901");
        book9.setPublicationYear(2020);
        book9.setPrice(21.99);
        bookRepository.save(book9);

        Book book10 = new Book();
        book10.setTitle("Modern Life");
        book10.setAuthor("Lisa Anderson");
        book10.setGenre(Genre.SATIRE);
        book10.setIsbn("978-7890123456");
        book10.setPublicationYear(2022);
        book10.setPrice(26.99);
        bookRepository.save(book10);
    }
}