package com.github.chrisgleissner.elastic.spring.book;

import com.github.chrisgleissner.elastic.spring.fixture.AbstractElasticIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BookRepoIT extends AbstractElasticIT {
    @Autowired BookRepo bookRepo;

    @Test
    void findByAuthor() {
        String author = "john";
        Book book = new Book(UUID.randomUUID().toString(), "book", author, 2020);
        bookRepo.save(book);
        try {
            List<Book> books = bookRepo.findByAuthor(author);
            assertThat(books).isNotEmpty();
            assertThat(books).extracting(Book::getAuthor).containsOnly(author);
        } finally {
            bookRepo.delete(book);
        }
    }
}