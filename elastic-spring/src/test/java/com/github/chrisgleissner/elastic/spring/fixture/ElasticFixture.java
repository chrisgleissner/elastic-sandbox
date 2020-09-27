package com.github.chrisgleissner.elastic.spring.fixture;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
    @Getter private final RestHighLevelClient client;
    @Getter private RequestOptions requestOptions = RequestOptions.DEFAULT;

    public ElasticFixture(ElasticsearchContainer container) {
        this.client = new RestHighLevelClient(RestClient.builder(HttpHost.create(container.getHttpHostAddress())));
    }

    public ElasticFixture withRequestOptions(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
        return this;
    }

    @SneakyThrows
    public void assertHealthy() {
        ClusterHealthResponse response = client.cluster().health(new ClusterHealthRequest(), requestOptions);
        assertThat(response.getNumberOfNodes()).isGreaterThanOrEqualTo(1);
    }
}
