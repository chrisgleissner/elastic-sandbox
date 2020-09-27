package com.github.chrisgleissner.elastic.spring.fixture;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ElasticFixture {
    @Getter private final RestHighLevelClient client;
    @Getter private final ElasticsearchRestTemplate restTemplate;
    @Getter private RequestOptions requestOptions = RequestOptions.DEFAULT;

    public ElasticFixture(ElasticsearchContainer container) {
        this.client = new RestHighLevelClient(RestClient.builder(HttpHost.create(container.getHttpHostAddress())));
        this.restTemplate = new ElasticsearchRestTemplate(this.client);
    }

    public ElasticFixture withRequestOptions(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
        return this;
    }

    @SneakyThrows
    public void logIndexMapping(String indexName) {
        log.info("Elasticsearch index mapping of {}: {}", indexName, client.indices()
                .getMapping(new GetMappingsRequest().indices(indexName), requestOptions)
                .mappings().get(indexName).source());
    }

    @SneakyThrows
    public void assertHealthy() {
        assertThat(client.cluster().health(new ClusterHealthRequest(), requestOptions).getNumberOfNodes()).isGreaterThanOrEqualTo(1);
    }

    public void updateIndexConfig(Class<?> indexClass, String indexName) {
        log.info("Updating settings of index {}", indexName);
        IndexOperations indexOps = restTemplate.indexOps(indexClass);
        indexOps.create();

        Document mapping = indexOps.createMapping();
        log.info("Updating mapping of index {} to be {}", indexName, mapping);
        indexOps.putMapping(mapping);

        logIndexMapping(indexName);
    }
}
