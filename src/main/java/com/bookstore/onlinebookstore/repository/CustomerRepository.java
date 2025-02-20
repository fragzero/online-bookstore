package com.bookstore.onlinebookstore.repository;

import com.bookstore.onlinebookstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
} 