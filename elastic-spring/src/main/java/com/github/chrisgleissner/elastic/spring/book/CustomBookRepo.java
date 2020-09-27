package com.github.chrisgleissner.elastic.spring.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component @RequiredArgsConstructor @Slf4j
public class CustomBookRepo {
    private final ElasticsearchRestTemplate restTemplate;

    public List<Book> findByOptionalWildcardArgs(String name, String author) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        Optional.ofNullable(name).ifPresent(n -> boolQueryBuilder.filter(QueryBuilders.wildcardQuery(Book.NAME, n)));
        Optional.ofNullable(author).ifPresent(a -> boolQueryBuilder.filter(QueryBuilders.wildcardQuery(Book.AUTHOR, a)));
        Query query = new NativeSearchQuery(boolQueryBuilder).setPageable(Pageable.unpaged());
        SearchHits<Book> hits = restTemplate.search(query, Book.class);
        List<Book> books = hits.get().map(SearchHit::getContent).collect(Collectors.toList());
        log.info("findByNameAndAuthorWithWildcards(name={}, author={}) found {} hit(s): hits={}, books={}",
                name, author, hits.getTotalHits(), hits, books);
        return books;
    }
}
