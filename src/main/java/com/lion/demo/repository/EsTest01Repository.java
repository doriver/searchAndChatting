package com.lion.demo.repository;

import com.lion.demo.entity.EsTest01;
import org.springframework.data
        .elasticsearch.repository.ElasticsearchRepository;

public interface EsTest01Repository
        extends ElasticsearchRepository<EsTest01, String> {
}
