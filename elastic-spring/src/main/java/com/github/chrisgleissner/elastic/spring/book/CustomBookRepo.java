package com.github.chrisgleissner.elastic.spring.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component @RequiredArgsConstructor @Slf4j
public class CustomBookRepo {
    private final ElasticsearchRestTemplate restTemplate;

    public List<Book> findByNameAndAuthorWithWildcards(String name, String author) {
        QueryBuilder qb = QueryBuilders.boolQuery()
                .filter(QueryBuilders.wildcardQuery(Book.NAME, name))
                .filter(QueryBuilders.wildcardQuery(Book.AUTHOR, author));
        Query query = new NativeSearchQuery(qb).setPageable(Pageable.unpaged());
        SearchHits<Book> hits = restTemplate.search(query, Book.class);
        List<Book> books = hits.get().map(SearchHit::getContent).collect(Collectors.toList());
        log.info("findByNameAndAuthorWithWildcards(name={}, author={}) found {} hit(s): hits={}, books={}",
                name, author, hits.getTotalHits(), hits, books);
        return books;
    }
}
