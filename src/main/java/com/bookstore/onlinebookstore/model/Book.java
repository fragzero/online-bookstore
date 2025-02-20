package com.bookstore.onlinebookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String isbn;

    @NotNull
    @Positive
    private BigDecimal basePrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BookType type;

    public enum BookType {
        NEW_RELEASE,
        REGULAR,
        OLD_EDITION
    }
} 