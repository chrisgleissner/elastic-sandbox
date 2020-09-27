package com.github.chrisgleissner.elastic.spring.book;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Value @NoArgsConstructor(force = true) @AllArgsConstructor
@Document(indexName = "#{T(com.github.chrisgleissner.elastic.spring.IndexNames).book}")
@Setting(settingPath = "/elastic/book-setting.json")
@Mapping(mappingPath = "/elastic/book-mapping.json")
public class Book {
    public static final String ISBN = "isbn";
    public static final String NAME = "name";
    public static final String AUTHOR = "author";
    public static final String YEAR = "year";

    @Id String isbn;
    String name;
    String author;
    int year;
}