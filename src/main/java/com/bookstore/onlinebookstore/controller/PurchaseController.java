package com.bookstore.onlinebookstore.controller;

import com.bookstore.onlinebookstore.model.Purchase;
import com.bookstore.onlinebookstore.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(
            @RequestParam Long customerId,
            @RequestParam List<Long> bookIds,
            @RequestParam(defaultValue = "false") boolean useLoyaltyPoints) {
        return ResponseEntity.ok(purchaseService.createPurchase(customerId, bookIds, useLoyaltyPoints));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Purchase>> getPurchasesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(purchaseService.getPurchasesByCustomerId(customerId));
    }
} 