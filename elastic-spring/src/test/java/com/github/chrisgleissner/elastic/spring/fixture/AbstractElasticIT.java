package com.github.chrisgleissner.elastic.spring.fixture;

import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractElasticIT {
    @Container private static final ElasticContainer container = new ElasticContainer();
    @Getter private static ElasticFixture fixture;

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
