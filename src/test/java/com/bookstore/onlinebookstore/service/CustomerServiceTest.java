package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.model.Customer;
import com.bookstore.onlinebookstore.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setLoyaltyPoints(0);
    }

    @Test
    void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));
        List<Customer> customers = customerService.getAllCustomers();
        assertEquals(1, customers.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Customer foundCustomer = customerService.getCustomerById(1L);
        assertNotNull(foundCustomer);
        assertEquals(customer.getId(), foundCustomer.getId());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void getCustomerById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerById(1L));
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void createCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer createdCustomer = customerService.createCustomer(customer);
        assertNotNull(createdCustomer);
        assertEquals(customer.getName(), createdCustomer.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void updateCustomer() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer updatedCustomer = customerService.updateCustomer(customer);
        assertNotNull(updatedCustomer);
        assertEquals(customer.getName(), updatedCustomer.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void deleteCustomer() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(1L);
        customerService.deleteCustomer(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCustomer_NotFound() {
        when(customerRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> customerService.deleteCustomer(1L));
        verify(customerRepository, times(0)).deleteById(1L);
    }
    
    @Test
    void getLoyaltyPoints() {
        customer.setLoyaltyPoints(5);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
         int points = customerService.getLoyaltyPoints(1L);
         assertEquals(5, points);
        verify(customerRepository, times(1)).findById(1L);
    }
    
    @Test
    void getLoyaltyPoints_CustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
         assertThrows(EntityNotFoundException.class, () -> customerService.getLoyaltyPoints(1L));
        verify(customerRepository, times(1)).findById(1L);
    }
} 