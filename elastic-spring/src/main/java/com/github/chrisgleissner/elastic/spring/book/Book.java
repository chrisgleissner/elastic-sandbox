package com.github.chrisgleissner.elastic.spring.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data @NoArgsConstructor @AllArgsConstructor
@Document(indexName = "book")
public class Book {
    public static final String ISBN = "isbn";
    public static final String NAME = "name";
    public static final String AUTHOR = "author";
    public static final String YEAR = "year";

    @Id private String isbn;
    @Field(type = FieldType.Text)
    private String name;
    private String author;
    private int year;
}