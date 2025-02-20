package com.bookstore.onlinebookstore.service.pricing;

import com.bookstore.onlinebookstore.model.Book;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class OldEditionPricingStrategy implements PricingStrategy {
    private static final BigDecimal BASE_DISCOUNT = new BigDecimal("0.20"); // 20% base discount
    private static final BigDecimal BUNDLE_DISCOUNT = new BigDecimal("0.05"); // Additional 5% bundle discount
    private static final int BUNDLE_SIZE = 3;

    @Override
    public BigDecimal calculatePrice(List<Book> books) {
        BigDecimal totalPrice = books.stream()
                .map(Book::getBasePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Apply base discount
        BigDecimal baseDiscountAmount = totalPrice.multiply(BASE_DISCOUNT);
        BigDecimal priceAfterBaseDiscount = totalPrice.subtract(baseDiscountAmount);

        // Apply bundle discount if applicable
        if (books.size() >= BUNDLE_SIZE) {
            BigDecimal bundleDiscountAmount = priceAfterBaseDiscount.multiply(BUNDLE_DISCOUNT);
            return priceAfterBaseDiscount.subtract(bundleDiscountAmount).setScale(2, RoundingMode.HALF_UP);
        }

        return priceAfterBaseDiscount.setScale(2, RoundingMode.HALF_UP);
    }
} 