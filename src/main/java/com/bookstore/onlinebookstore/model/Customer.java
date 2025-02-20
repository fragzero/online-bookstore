package com.bookstore.onlinebookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    private int loyaltyPoints;

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    public void resetLoyaltyPoints() {
        this.loyaltyPoints = 0;
    }

    public boolean hasEnoughPointsForFreeBook() {
        return this.loyaltyPoints >= 10;
    }
} 