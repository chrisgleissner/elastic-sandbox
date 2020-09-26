package com.github.chrisgleissner.elastic.spring.fixture;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractElasticIT {
    @Container private static final ElasticContainer container = new ElasticContainer();
    private static ElasticFixture fixture;

    protected ElasticFixture fixture() {
        return fixture;
    }

    @BeforeAll
    static void setUp() {
        container.start();
        fixture = new ElasticFixture(container);
    }

    @AfterAll
    static void destroy() {
        container.stop();
    }
}
