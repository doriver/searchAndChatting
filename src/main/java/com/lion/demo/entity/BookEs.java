package com.lion.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "books") // @Entity와 비슷한 역할
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEs {
    @Id
    private String bookId; // UUD스타일

    private String title;
    private String author;
    private String company;
    private int price;
    private String imageUrl;
    private String summary;

}
