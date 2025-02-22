package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Customer;
import com.bookstore.onlinebookstore.model.Purchase;
import com.bookstore.onlinebookstore.repository.PurchaseRepository;
import com.bookstore.onlinebookstore.service.pricing.NewReleasePricingStrategy;
import com.bookstore.onlinebookstore.service.pricing.OldEditionPricingStrategy;
import com.bookstore.onlinebookstore.service.pricing.RegularPricingStrategy;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private BookService bookService;

    @Mock
    private NewReleasePricingStrategy newReleasePricingStrategy;

    @Mock
    private RegularPricingStrategy regularPricingStrategy;

    @Mock
    private OldEditionPricingStrategy oldEditionPricingStrategy;

    @InjectMocks
    private PurchaseService purchaseService;

    private Customer customer;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setLoyaltyPoints(0);

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setIsbn("1234567890");
        book.setBasePrice(new BigDecimal("10.00"));
        book.setType(Book.BookType.REGULAR);
    }

    @Test
    void createPurchase() {
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(bookService.getBookById(1L)).thenReturn(book);
        when(regularPricingStrategy.calculatePrice(anyList())).thenReturn(new BigDecimal("10.00"));
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(new Purchase());

        Purchase purchase = purchaseService.createPurchase(1L, Arrays.asList(1L), false);

        assertNotNull(purchase);
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
    }

    @Test
    void createPurchase_UseLoyaltyPoints() {
        customer.setLoyaltyPoints(10);
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(bookService.getBookById(1L)).thenReturn(book);
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(new Purchase());

        Purchase purchase = purchaseService.createPurchase(1L, Arrays.asList(1L), true);

        assertNotNull(purchase);
        assertEquals(null, purchase.getTotalPrice());
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
    }

    @Test
    void createPurchase_NotEnoughLoyaltyPoints() {
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(bookService.getBookById(1L)).thenReturn(book);

        assertThrows(IllegalStateException.class, () -> purchaseService.createPurchase(1L, Arrays.asList(1L), true));
    }

    @Test
    void getPurchasesByCustomerId() {
        when(customerService.customerExists(1L)).thenReturn(true);
        when(purchaseRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(new Purchase()));

        List<Purchase> purchases = purchaseService.getPurchasesByCustomerId(1L);

        assertEquals(1, purchases.size());
        verify(purchaseRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void getPurchasesByCustomerId_CustomerNotFound() {
        when(customerService.customerExists(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> purchaseService.getPurchasesByCustomerId(1L));
    }
} 