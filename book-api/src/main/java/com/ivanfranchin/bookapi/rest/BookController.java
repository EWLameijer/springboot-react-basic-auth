package com.ivanfranchin.bookapi.rest;

import com.ivanfranchin.bookapi.model.Book;
import com.ivanfranchin.bookapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private final BookService bookService;

    @GetMapping
    public List<Book> getBooks(@RequestParam(value = "text", required = false) String text) {
        return text == null ? bookService.getBooks() : bookService.getBooksContainingText(text);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @DeleteMapping("/{isbn}")
    public Book deleteBook(@PathVariable String isbn) {
        Book book = bookService.validateAndGetBook(isbn);
        bookService.deleteBook(book);
        return book;
    }
}
