package com.lion.demo.controller;

import com.lion.demo.service.CsvFileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/csvTest")
public class CsvTestController {

    @Autowired
    CsvFileReaderService csvFileReaderService;

    @RequestMapping("/1")
    public void aa() {
        csvFileReaderService.csvFileReader();
    }
}
