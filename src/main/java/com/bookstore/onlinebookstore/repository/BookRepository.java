package com.bookstore.onlinebookstore.repository;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Book.BookType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByType(BookType type);
} 