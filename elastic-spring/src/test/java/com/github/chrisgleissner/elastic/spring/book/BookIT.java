package com.github.chrisgleissner.elastic.spring.book;

import com.github.chrisgleissner.elastic.spring.fixture.AbstractElasticIT;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) @Slf4j
class BookIT extends AbstractElasticIT {
    public static final String ISBN1 = "11";
    public static final String ISBN2 = "12";
    public static final String ISBN3 = "21";

    private static final String NAME_COMMON_PREFIX = "book name";
    private static final String NAME1 = NAME_COMMON_PREFIX + " 11";
    private static final String NAME2 = NAME_COMMON_PREFIX + " 12";
    private static final String NAME3 = NAME_COMMON_PREFIX + " 21";

    private static final String AUTHOR_COMMON_PREFIX = "joe";
    private static final String AUTHOR1 = AUTHOR_COMMON_PREFIX + " doe";
    private static final String AUTHOR2 = AUTHOR_COMMON_PREFIX + " don doe";

    private static final int PUBLICATION_YEAR_1 = 2001;
    private static final int PUBLICATION_YEAR_2 = 2002;
    private static final int PUBLICATION_YEAR_3 = 2003;

    private static final Book BOOK1 = new Book(ISBN1, NAME1, AUTHOR1, PUBLICATION_YEAR_1);
    private static final Book BOOK2 = new Book(ISBN2, NAME2, AUTHOR1, PUBLICATION_YEAR_2);
    private static final Book BOOK3 = new Book(ISBN3, NAME3, AUTHOR2, PUBLICATION_YEAR_3);

    @Autowired ElasticsearchRestTemplate restTemplate;
    @Autowired BookRepo bookRepo;
    @Autowired CustomBookRepo customBookRepo;

    @BeforeAll
    void setup() {
        getFixture().updateIndexConfig(Book.class, Book.INDEX_NAME);

        bookRepo.saveAll(List.of(
                // Written by joe doe:
                BOOK1, // book name 11, 2001
                BOOK2, // book name 12, 2002
                // Written by joe don doe:
                BOOK3 // book name21, 2003
        ));
        getFixture().logIndexMapping(Book.INDEX_NAME);
    }

    @AfterAll
    void tearDown() {
        bookRepo.deleteAll();
    }

    @Test
    void findByAuthor() {
        assertThat(bookRepo.findByAuthor(AUTHOR1)).containsExactly(BOOK1, BOOK2);
    }

    @Test
    void findByAuthorStartingWithPrefix() {
        assertThat(bookRepo.findByAuthorStartingWith(AUTHOR_COMMON_PREFIX)).containsExactly(BOOK1, BOOK2, BOOK3);
    }

    @Test
    void findByNameAndAuthorWithWildcards() {
        assertThat(customBookRepo.findByNameAndAuthorWithWildcards(NAME_COMMON_PREFIX + "*", AUTHOR1)).containsExactly(BOOK1, BOOK2);
    }
}