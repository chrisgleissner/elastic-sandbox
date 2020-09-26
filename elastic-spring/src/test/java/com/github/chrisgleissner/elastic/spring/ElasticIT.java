package com.github.chrisgleissner.elastic.spring;

import com.github.chrisgleissner.elastic.spring.fixture.AbstractElasticIT;
import org.junit.jupiter.api.Test;

public class ElasticIT extends AbstractElasticIT {

    @Test
    void healthy() {
        fixture().assertHealthy();
    }
}
