package com.lion.demo.controller;

import com.lion.demo.entity.Book;
import com.lion.demo.service.BookService;
import com.lion.demo.service.CsvFileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired private CsvFileReaderService csvFileReaderService;
    @Autowired private BookService bookService;

    @GetMapping("/yes24")
    public String yes24() {
        csvFileReaderService.csvFileToH2();
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(@RequestParam(name="p", defaultValue = "1") int page,
                       @RequestParam(name="f", defaultValue = "title") String field,
                       @RequestParam(name="q", defaultValue = "") String query,
            Model model) {

//        List<Book> bookList = bookService.getBooksByPage(page); // 이쪽 if문으로 해줘야함
        List<Book> bookList = bookService.getBookList(page,field,query);
        model.addAttribute("bookList", bookList);
        return "book/list";
    }

    @GetMapping("/insert")
    public String insertForm() {
        return "book/insert";
    }

    @PostMapping("/insert")
    public String insertPoc(Book book) { // 화면에서 bid를 0으로 넘겨주면 insert할때 수정해서 맞게 넣어줌
        bookService.insertBook(book);
        return "redirect:/book/list";
    }

}
