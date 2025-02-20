package com.bookstore.onlinebookstore.service.pricing;

import com.bookstore.onlinebookstore.model.Book;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class RegularPricingStrategy implements PricingStrategy {
    private static final BigDecimal BUNDLE_DISCOUNT = new BigDecimal("0.10"); // 10% discount
    private static final int BUNDLE_SIZE = 3;

    @Override
    public BigDecimal calculatePrice(List<Book> books) {
        BigDecimal totalPrice = books.stream()
                .map(Book::getBasePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (books.size() >= BUNDLE_SIZE) {
            BigDecimal discount = totalPrice.multiply(BUNDLE_DISCOUNT);
            return totalPrice.subtract(discount).setScale(2, RoundingMode.HALF_UP);
        }

        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }
} 