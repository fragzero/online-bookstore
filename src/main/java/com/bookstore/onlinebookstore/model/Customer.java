package com.bookstore.onlinebookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public static class LoyaltyPointsResponse {
        @JsonProperty("loyalty_points")
        private final int loyaltyPoints;

        public LoyaltyPointsResponse(int loyaltyPoints) {
            this.loyaltyPoints = loyaltyPoints;
        }

        public int getLoyaltyPoints() {
            return loyaltyPoints;
        }
    }
} 