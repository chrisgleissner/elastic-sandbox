package com.github.chrisgleissner.elastic.spring.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data @NoArgsConstructor @AllArgsConstructor
@Document(indexName = "book")
public class Book {
    @Id private String isbn;
    private String name;
    private String author;
    private int publicationYear;
}