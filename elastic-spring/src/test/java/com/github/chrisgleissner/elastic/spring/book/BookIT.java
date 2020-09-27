package com.github.chrisgleissner.elastic.spring.book;

import com.github.chrisgleissner.elastic.spring.fixture.AbstractElasticIT;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.chrisgleissner.elastic.spring.book.BookIT.TestData.AUTHOR1;
import static com.github.chrisgleissner.elastic.spring.book.BookIT.TestData.AUTHOR2_UNIQUE_STRING;
import static com.github.chrisgleissner.elastic.spring.book.BookIT.TestData.AUTHOR_COMMON_PREFIX;
import static com.github.chrisgleissner.elastic.spring.book.BookIT.TestData.BOOK1;
import static com.github.chrisgleissner.elastic.spring.book.BookIT.TestData.BOOK2;
import static com.github.chrisgleissner.elastic.spring.book.BookIT.TestData.BOOK3;
import static com.github.chrisgleissner.elastic.spring.book.BookIT.TestData.BOOKS;
import static com.github.chrisgleissner.elastic.spring.book.BookIT.TestData.NAME_COMMON_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class BookIT extends AbstractElasticIT {
    @Autowired private BookRepo bookRepo;
    @Autowired private CustomBookRepo customBookRepo;

    @Test
    void findByAuthor() {
        // Exact match since author is of type keyword. If we this field were of type text, we'd get all 3 books.
        assertThat(bookRepo.findByAuthor(AUTHOR1)).containsExactly(BOOK1, BOOK2);
    }

    @Test
    void findByAuthorStartingWith() {
        assertThat(bookRepo.findByAuthorStartingWith(AUTHOR_COMMON_PREFIX)).containsExactlyElementsOf(BOOKS);
    }

    @Test
    void findByOptionalWildcardArgsUsingNameAndAuthor() {
        assertThat(customBookRepo.findByOptionalWildcardArgs(NAME_COMMON_PREFIX + "*", AUTHOR1)).containsExactly(BOOK1, BOOK2);
    }

    @Test
    void findByOptionalWildcardArgsUsingAuthor() {
        assertThat(customBookRepo.findByOptionalWildcardArgs(null, AUTHOR_COMMON_PREFIX + "*")).containsExactlyElementsOf(BOOKS);
    }

    @Test
    void findByOptionalWildcardArgsUsingAuthorWithLeadingWildcard() {
        assertThat(customBookRepo.findByOptionalWildcardArgs(null, "*" + AUTHOR2_UNIQUE_STRING + "*")).containsExactly(BOOK3);
    }

    interface TestData {
        String ISBN1 = "11";
        String ISBN2 = "12";
        String ISBN3 = "21";

        String NAME_COMMON_PREFIX = "book name";
        String NAME1 = NAME_COMMON_PREFIX + " 11";
        String NAME2 = NAME_COMMON_PREFIX + " 12";
        String NAME3 = NAME_COMMON_PREFIX + " 21";

        String AUTHOR_COMMON_PREFIX = "joe";
        String AUTHOR1 = AUTHOR_COMMON_PREFIX + " doe";
        String AUTHOR2_UNIQUE_STRING = "don";
        String AUTHOR2 = AUTHOR_COMMON_PREFIX + " " + AUTHOR2_UNIQUE_STRING + " doe";

        int PUBLICATION_YEAR_1 = 2001;
        int PUBLICATION_YEAR_2 = 2002;
        int PUBLICATION_YEAR_3 = 2003;

        // Written by joe doe:
        Book BOOK1 = new Book(ISBN1, NAME1, AUTHOR1, PUBLICATION_YEAR_1);
        Book BOOK2 = new Book(ISBN2, NAME2, AUTHOR1, PUBLICATION_YEAR_2);

        // Written by joe don doe:
        Book BOOK3 = new Book(ISBN3, NAME3, AUTHOR2, PUBLICATION_YEAR_3);

        List<Book> BOOKS = List.of(BOOK1, BOOK2, BOOK3);
    }

    @BeforeAll
    void setup() {
        getFixture().updateIndexConfig(Book.class, Book.INDEX_NAME);
        bookRepo.saveAll(BOOKS);
        getFixture().logIndexMapping(Book.INDEX_NAME);
    }

    @AfterAll
    void tearDown() {
        bookRepo.deleteAll();
    }
}