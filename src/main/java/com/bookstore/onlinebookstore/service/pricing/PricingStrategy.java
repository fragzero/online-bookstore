package com.bookstore.onlinebookstore.service.pricing;

import java.math.BigDecimal;
import java.util.List;

import com.bookstore.onlinebookstore.model.Book;

public interface PricingStrategy {
    BigDecimal calculatePrice(List<Book> books);
} 