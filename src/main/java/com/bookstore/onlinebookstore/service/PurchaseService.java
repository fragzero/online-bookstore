package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Customer;
import com.bookstore.onlinebookstore.model.Purchase;
import com.bookstore.onlinebookstore.repository.PurchaseRepository;
import com.bookstore.onlinebookstore.service.pricing.NewReleasePricingStrategy;
import com.bookstore.onlinebookstore.service.pricing.OldEditionPricingStrategy;
import com.bookstore.onlinebookstore.service.pricing.RegularPricingStrategy;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final CustomerService customerService;
    private final BookService bookService;
    private final NewReleasePricingStrategy newReleasePricingStrategy;
    private final RegularPricingStrategy regularPricingStrategy;
    private final OldEditionPricingStrategy oldEditionPricingStrategy;

    @Transactional
    public Purchase createPurchase(Long customerId, List<Long> bookIds, boolean useLoyaltyPoints) {
        Customer customer = customerService.getCustomerById(customerId);
        List<Book> books = bookIds.stream()
                .map(bookService::getBookById)
                .collect(Collectors.toList());

        if (useLoyaltyPoints && !customer.hasEnoughPointsForFreeBook()) {
            throw new IllegalStateException("Not enough loyalty points for a free book");
        }

        BigDecimal totalPrice = calculateTotalPrice(books, useLoyaltyPoints);

        Purchase purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setBooks(books);
        purchase.setTotalPrice(totalPrice);
        purchase.setUsedLoyaltyPoints(useLoyaltyPoints);

        if (useLoyaltyPoints) {
            customer.resetLoyaltyPoints();
        } else {
            customer.addLoyaltyPoints(books.size());
        }

        customerService.updateCustomer(customer);
        return purchaseRepository.save(purchase);
    }

    private BigDecimal calculateTotalPrice(List<Book> books, boolean useLoyaltyPoints) {
        if (useLoyaltyPoints) {
            return BigDecimal.ZERO;
        }

        Map<Book.BookType, List<Book>> booksByType = books.stream()
                .collect(Collectors.groupingBy(Book::getType));

        BigDecimal total = BigDecimal.ZERO;

        if (booksByType.containsKey(Book.BookType.NEW_RELEASE)) {
            total = total.add(newReleasePricingStrategy.calculatePrice(booksByType.get(Book.BookType.NEW_RELEASE)));
        }
        if (booksByType.containsKey(Book.BookType.REGULAR)) {
            total = total.add(regularPricingStrategy.calculatePrice(booksByType.get(Book.BookType.REGULAR)));
        }
        if (booksByType.containsKey(Book.BookType.OLD_EDITION)) {
            total = total.add(oldEditionPricingStrategy.calculatePrice(booksByType.get(Book.BookType.OLD_EDITION)));
        }

        return total;
    }

    public List<Purchase> getPurchasesByCustomerId(Long customerId) {
        if (!customerService.customerExists(customerId)) {
            throw new EntityNotFoundException("Customer not found with id: " + customerId);
        }
        return purchaseRepository.findByCustomerId(customerId);
    }
} 