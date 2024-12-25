package com.lion.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lion.demo.entity.Book;
import com.lion.demo.entity.BookEs;
import com.lion.demo.entity.Restaurant;
import com.lion.demo.repository.RestaurantRepository;
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
    @Autowired private BookEsService bookEsService;
    @Autowired private RestaurantRepository restaurantRepository;

    public void csvFileReader() {
        try {
            Resource resource = resourceLoader.getResource("classpath:static/data/okkyOne.csv");

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


    public void csvFileToElasticsearch() {
        try {
            Resource resource = resourceLoader.getResource("classpath:static/data/yes24_국내도서_새로나온_상품.csv");

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
                    BookEs bookEs = BookEs.builder()
                            .title(title).author(author).company(company).price(price)
                            .imageUrl(imageUrl).summary(summary)
                            .build();
                    bookEsService.insertBook(bookEs);

                    if (count ++ % 1000 == 0) {
                        System.out.println("count = " + count);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restaurantSeoulToElasticSearch() {
        try {
            Resource resource = resourceLoader.getResource("classpath:static/data/서울맛집3.csv");

            try (
                    Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()) ) {
                int count = 0;
                for (CSVRecord record: csvParser) {
                    String name = record.get("상호");
                    String intro = record.get("설명");
                    String imgSrc = record.get("img_src");
                    String infoJsonStr = record.get("업소정보");
                    String reviewJsonStr = record.get("리뷰");

                    ObjectMapper objectMapper = new ObjectMapper(); // jackson databind
                    try {
                        Map<String,Object> info = objectMapper.readValue(infoJsonStr, Map.class);
                        List<Map<String,Object>> reviews = objectMapper.readValue(reviewJsonStr, new TypeReference<>() { });

                        Restaurant restaurant = Restaurant.builder()
                                .name(name).intro(intro).imgSrc(imgSrc).info(info).reviews(reviews)
                                .build();

                        restaurantRepository.save(restaurant);

                        if (count ++ % 100 == 0) {
                            System.out.println("count = " + count);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
