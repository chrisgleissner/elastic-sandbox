package com.github.chrisgleissner.elastic.spring;

import com.github.chrisgleissner.elastic.spring.fixture.AbstractElasticIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

public class ElasticIT extends AbstractElasticIT {
    @Autowired private ElasticsearchRestTemplate restTemplate;

    @Test
    void healthy() {
        getFixture().assertHealthy();
    }
}
