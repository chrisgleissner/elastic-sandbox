package com.github.chrisgleissner.elastic.spring.fixture;

import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractElasticIT {
    @Container private static final ElasticContainer elasticContainer = new ElasticContainer();
    @Getter private static ElasticFixture fixture;

    @BeforeAll
    static void startElastic() {
        fixture = new ElasticFixture(elasticContainer);
        fixture.assertHealthy();
    }

    @AfterAll
    static void stopElastic() {
        elasticContainer.stop();
    }
}
