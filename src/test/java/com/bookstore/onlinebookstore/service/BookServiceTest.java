package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setIsbn("1234567890");
        book.setBasePrice(new BigDecimal("10.00"));
        book.setType(Book.BookType.REGULAR);
    }

    @Test
    void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        List<Book> books = bookService.getAllBooks();
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Book foundBook = bookService.getBookById(1L);
        assertNotNull(foundBook);
        assertEquals(book.getId(), foundBook.getId());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void createBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book createdBook = bookService.createBook(book);
        assertNotNull(createdBook);
        assertEquals(book.getTitle(), createdBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book updatedBook = bookService.updateBook(1L, book);
        assertNotNull(updatedBook);
        assertEquals(book.getTitle(), updatedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void deleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_NotFound() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, times(0)).deleteById(1L);
    }
} 