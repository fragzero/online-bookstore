package com.bookstore.onlinebookstore.service.pricing;

import com.bookstore.onlinebookstore.model.Book;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class NewReleasePricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal calculatePrice(List<Book> books) {
        return books.stream()
                .map(Book::getBasePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 