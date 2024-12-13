package com.lion.demo.controller;


import com.lion.demo.service.BookEsService;
import com.lion.demo.service.CsvFileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bookEs")
public class BookEsController {

    @Autowired private BookEsService bookEsService;
    @Autowired private CsvFileReaderService csvFileReaderService;

    @GetMapping("/yes24")
    @ResponseBody
    public String yes24() {
        csvFileReaderService.csvFileToElasticsearch();
        return "<h1>elasticsearch에 데이터를 저장했습니다.</h1>";
    }

}
