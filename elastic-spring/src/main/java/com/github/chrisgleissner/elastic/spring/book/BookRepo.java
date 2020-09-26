package com.github.chrisgleissner.elastic.spring.book;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepo extends ElasticsearchRepository<Book, String> {
    List<Book> findByAuthor(String author);
}
