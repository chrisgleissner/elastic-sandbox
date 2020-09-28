package com.github.chrisgleissner.elastic.spring.util;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;

@RequiredArgsConstructor
public class ElasticAdmin {
    private RestHighLevelClient client;
    private RequestOptions requestOptions;

    @SneakyThrows
    public CreateIndexResponse createIndex(String indexName, String mappingJson, String settingsJson) {
        CreateIndexRequest request = new CreateIndexRequest(indexName)
                .mapping(mappingJson, XContentType.JSON)
                .settings(settingsJson, XContentType.JSON);
        return client.indices().create(request, requestOptions);
    }
}
