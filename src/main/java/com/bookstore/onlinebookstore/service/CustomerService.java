package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.model.Customer;
import com.bookstore.onlinebookstore.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    public boolean customerExists(Long id) {
        return customerRepository.existsById(id);
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Customer customer) {
        if (!customerRepository.existsById(customer.getId())) {
            throw new EntityNotFoundException("Customer not found with id: " + customer.getId());
        }
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    public int getLoyaltyPoints(Long customerId) {
        Customer customer = getCustomerById(customerId);
        return customer.getLoyaltyPoints();
    }
} 