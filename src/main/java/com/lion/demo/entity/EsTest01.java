package com.lion.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "foods") // @Entity와 비슷한 역할
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsTest01 {
    @Id
    private String foodId;
    private String name;
    private int price;
}
