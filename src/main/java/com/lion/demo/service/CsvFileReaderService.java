package com.lion.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lion.demo.entity.Book;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class CsvFileReaderService { // 파일읽어서 DB에 넣을꺼
    @Autowired private ResourceLoader resourceLoader;
    @Autowired private BookService bookService;

    public void csvFileReader() {
        try {
            Resource resource = resourceLoader.getResource("classpath:static/data/okkyThree.csv");

            try (
                    Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()) ) {
                int count = 0;
                for (CSVRecord record: csvParser) {
                    String category = record.get("category");
                    String tag = record.get("tag");
                    String user = record.get("user");
                    String title = record.get("title");
                    String content = record.get("content");
                    String imgSrc = record.get("imgSrc");
                    String createAt = record.get("createAt");
                    String viewCount = record.get("viewCount");
                    String likeCount = record.get("likeCount");
                    String postReplyLists = record.get("postReplyLists");

                    System.out.println("       =========     ===========    ==========   ");
                    System.out.println(category);
                    System.out.println(tag);
                    System.out.println(user);
                    System.out.println(title);
                    System.out.println(content);
                    System.out.println(imgSrc);
                    System.out.println(createAt);
                    System.out.println(viewCount);
                    System.out.println(likeCount);
                    System.out.println(postReplyLists);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void csvFileToH2() {
        try {
            Resource resource = resourceLoader.getResource("classpath:static/data/20241114_yes24_국내도서_새로나온_상품.csv");

            try (
                    Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()) ) {
                int count = 0;
                for (CSVRecord record: csvParser) {
                    String title = record.get("title");
                    String author = record.get("author");
                    String company = record.get("company");
                    String _price = record.get("price");
                    int price = Integer.parseInt(_price);
                    String imageUrl = record.get("imageUrl");
                    String summary = record.get("summary");
                    Book book = Book.builder()
                            .title(title).author(author).company(company).price(price)
                            .imageUrl(imageUrl).summary(summary)
                            .build();
                    bookService.insertBook(book);

                    if (count ++ == 100) {
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
