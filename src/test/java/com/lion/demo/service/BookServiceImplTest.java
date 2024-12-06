package com.lion.demo.service;

import com.lion.demo.entity.Book;
import com.lion.demo.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @Mock private BookRepository bookRepository;
    @InjectMocks private BookServiceImpl bookService; // BookServiceImpl생성해서 Mock객체인 bookRepository주입

    @BeforeEach // 테스트 메소드 실행할때마다 수행
    void setup() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
    }
    @Test
    void testFindByBid() {
        // given
        Book book = Book.builder()
                .bid(999).title("title").price(23000)
                .build();

        when(bookRepository.findById(999L)).thenReturn(Optional.of(book)); // 이거 뭐냐

        // when
        Book foundBook = bookService.findByBid(999L);

        // then
        assertThat(foundBook).isNotNull();
        assertThat(foundBook.getTitle()).isEqualTo("title");
        assertThat(foundBook.getPrice()).isEqualTo(23000);
    }

    @Test
    void testGetBooksByPage() {
        Book book1 = Book.builder().bid(999L).title("title1").price(23000).build();
        Book book2 = Book.builder().bid(1000L).title("title2").price(24000).build();
        List<Book> bookList = Arrays.asList(book1, book2);

        Pageable pageable = PageRequest.of(0, BookService.PAGE_SIZE);
        Page<Book> bookPage = new PageImpl<>(bookList, pageable, bookList.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        // when
        List<Book> foundBookList = bookService.getBooksByPage(1);

        // then
        assertThat(foundBookList).hasSize(2);
        assertThat(foundBookList.get(0).getTitle()).isEqualTo("title1");
        assertThat(foundBookList.get(1).getPrice()).isEqualTo(24000);
    }




}
