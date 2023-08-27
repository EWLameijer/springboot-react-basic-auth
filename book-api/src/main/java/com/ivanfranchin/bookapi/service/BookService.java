package com.ivanfranchin.bookapi.service;

import com.ivanfranchin.bookapi.model.Book;
import com.ivanfranchin.bookapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getBooks() {
        return bookRepository.findAllByOrderByTitle();
    }

    public List<Book> getBooksContainingText(String text) {
        return bookRepository.findByIsbnContainingOrTitleContainingIgnoreCaseOrderByTitle(text, text);
    }

    public Book validateAndGetBook(String isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException(String.format("Book with isbn %s not found", isbn)));
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }
}
