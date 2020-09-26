package com.github.chrisgleissner.elastic.spring.fixture;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import static org.assertj.core.api.Assertions.assertThat;

public class ElasticFixture {
    @Getter
    private final RestHighLevelClient client;

    public ElasticFixture(ElasticsearchContainer container) {
        this.client = new RestHighLevelClient(RestClient.builder(HttpHost.create(container.getHttpHostAddress())));
    }

    @SneakyThrows
    public void assertHealthy() {
        ClusterHealthRequest request = new ClusterHealthRequest();
        ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
        assertThat(response.getNumberOfNodes()).isGreaterThanOrEqualTo(1);
    }
}
