package com.bookstore.onlinebookstore.controller;

import com.bookstore.onlinebookstore.model.Customer;
import com.bookstore.onlinebookstore.model.Customer.LoyaltyPointsResponse;
import com.bookstore.onlinebookstore.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        customer.setId(id);
        return ResponseEntity.ok(customerService.updateCustomer(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/loyalty/{customerId}")
    public ResponseEntity<LoyaltyPointsResponse> getLoyaltyPoints(@PathVariable Long customerId) {
        int points = customerService.getLoyaltyPoints(customerId);
        return ResponseEntity.ok(new LoyaltyPointsResponse(points));
    }


}