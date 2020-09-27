package com.github.chrisgleissner.elastic.spring.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.Setting;

import static com.github.chrisgleissner.elastic.spring.book.Book.INDEX_NAME;

@Data @NoArgsConstructor @AllArgsConstructor
@Document(indexName = INDEX_NAME)
@Setting(settingPath = "/elastic/book-setting.json")
// Alternative for annotation-based index mapping
//@Mapping(mappingPath = "/elastic/book-mapping.json")
public class Book {
    public static final String INDEX_NAME = "book";

    public static final String ISBN = "isbn";
    public static final String NAME = "name";
    public static final String AUTHOR = "author";
    public static final String YEAR = "year";

    @Id
    private String isbn;

    @MultiField(mainField = @Field(type = FieldType.Keyword),
            otherFields = {@InnerField(suffix = "text", type = FieldType.Text, store = true)}
    )
    private String name;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Integer)
    private int year;
}