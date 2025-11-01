package com.lib.service;

import com.lib.model.Book;

import java.util.List;

public interface LibraryService {

    void login(String username);

    void logout();

    void addBook(String bookName);

    List<Book> listBooks();

    void borrow(String bookName);

    void returnBook(String bookName);

    void waitlist(String bookName);

    void status();
}
