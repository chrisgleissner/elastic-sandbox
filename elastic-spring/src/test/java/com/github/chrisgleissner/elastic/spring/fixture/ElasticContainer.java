package com.github.chrisgleissner.elastic.spring.fixture;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

public class ElasticContainer extends ElasticsearchContainer {
    private static final String ELASTIC_SEARCH_DOCKER = "elasticsearch:7.9.0";
    private static final String CLUSTER_NAME = "cluster.name";
    private static final String ELASTIC_SEARCH = "elasticsearch";
    private static final int DEFAULT_PORT = 9200;
    private static final int DEFAULT_TCP_PORT = 9300;

    public ElasticContainer() {
        super(ELASTIC_SEARCH_DOCKER);
        this.addEnv("xpack.license.self_generated.type", "basic");
        this.addFixedExposedPort(DEFAULT_PORT, DEFAULT_PORT);
        this.addFixedExposedPort(DEFAULT_TCP_PORT, DEFAULT_TCP_PORT);
        this.addEnv(CLUSTER_NAME, ELASTIC_SEARCH);
    }
}
