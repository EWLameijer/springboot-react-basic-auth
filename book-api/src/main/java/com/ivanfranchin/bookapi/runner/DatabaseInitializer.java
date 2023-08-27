package com.ivanfranchin.bookapi.runner;

import com.ivanfranchin.bookapi.model.Book;
import com.ivanfranchin.bookapi.model.User;
import com.ivanfranchin.bookapi.security.WebSecurityConfig;
import com.ivanfranchin.bookapi.service.BookService;
import com.ivanfranchin.bookapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserService userService;
    private final BookService bookService;

    @Override
    public void run(String... args) {
        if (!userService.getUsers().isEmpty()) {
            return;
        }
        USERS.forEach(userService::saveUser);
        getBooks().forEach(bookService::saveBook);
        System.out.println("Database initialized");
    }

    private List<Book> getBooks() {
        return Arrays.stream(BOOKS_STR.split("\n"))
                .map(bookInfoStr -> bookInfoStr.split(";"))
                .map(bookInfoArr -> new Book(bookInfoArr[0], bookInfoArr[1]))
                .collect(Collectors.toList());
    }

    private static final List<User> USERS = Arrays.asList(
            new User("admin", "admin", "Admin", "admin@mycompany.com", WebSecurityConfig.ADMIN),
            new User("user", "user", "User", "user@mycompany.com", WebSecurityConfig.USER)
    );

    private static final String BOOKS_STR =
            """
                    9781603090773;Any Empire
                    9781603090698;August Moon
            """;
}
