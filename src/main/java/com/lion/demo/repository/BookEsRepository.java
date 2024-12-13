package com.lion.demo.repository;

import com.lion.demo.entity.Book;
import com.lion.demo.entity.BookEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

public interface BookEsRepository extends ElasticsearchRepository<BookEs, String> {
    /*
        BookRepository에 있는 함수들 그대로 가져다씀
     */
    Page<BookEs> findAll(Pageable pageable); // 페이지 단위로 읽을수 있음

    // select * from bookEs where title like '$title$'
    Page<BookEs> findByTitleContaining(String title, Pageable pageable);
    Page<BookEs> findByAuthorContaining(String author, Pageable pageable);
    Page<BookEs> findByCompanyContaining(String company, Pageable pageable);
    Page<BookEs> findBySummaryContaining(String summary, Pageable pageable);

    Page<BookEs> findByPriceBetween(int minPrice, int maxPrice, Pageable pageable);

}
