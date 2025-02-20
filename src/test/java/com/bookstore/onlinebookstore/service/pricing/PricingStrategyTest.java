package com.bookstore.onlinebookstore.service.pricing;

import com.bookstore.onlinebookstore.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingStrategyTest {

    private NewReleasePricingStrategy newReleasePricingStrategy;
    private RegularPricingStrategy regularPricingStrategy;
    private OldEditionPricingStrategy oldEditionPricingStrategy;
    private Book book;

    @BeforeEach
    void setUp() {
        newReleasePricingStrategy = new NewReleasePricingStrategy();
        regularPricingStrategy = new RegularPricingStrategy();
        oldEditionPricingStrategy = new OldEditionPricingStrategy();

        book = new Book();
        book.setBasePrice(new BigDecimal("100.00"));
    }

    @Test
    void newReleasePricingStrategy_SingleBook() {
        book.setType(Book.BookType.NEW_RELEASE);
        BigDecimal price = newReleasePricingStrategy.calculatePrice(Collections.singletonList(book));
        assertEquals(new BigDecimal("100.00"), price);
    }

    @Test
    void regularPricingStrategy_SingleBook() {
        book.setType(Book.BookType.REGULAR);
        BigDecimal price = regularPricingStrategy.calculatePrice(Collections.singletonList(book));
        assertEquals(new BigDecimal("100.00"), price.setScale(2));
    }

    @Test
    void regularPricingStrategy_ThreeBooks() {
        book.setType(Book.BookType.REGULAR);
        BigDecimal price = regularPricingStrategy.calculatePrice(Arrays.asList(book, book, book));
        assertEquals(new BigDecimal("270.00"), price);
    }

    @Test
    void oldEditionPricingStrategy_SingleBook() {
        book.setType(Book.BookType.OLD_EDITION);
        BigDecimal price = oldEditionPricingStrategy.calculatePrice(Collections.singletonList(book));
        assertEquals(new BigDecimal("80.00"), price);
    }

    @Test
    void oldEditionPricingStrategy_ThreeBooks() {
        book.setType(Book.BookType.OLD_EDITION);
        BigDecimal price = oldEditionPricingStrategy.calculatePrice(Arrays.asList(book, book, book));
        assertEquals(new BigDecimal("228.00"), price);
    }
} 