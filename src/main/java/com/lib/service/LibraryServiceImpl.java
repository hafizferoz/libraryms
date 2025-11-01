package com.lib.service;

import com.lib.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService{

    @Override
    public void login(String username) {

    }

    @Override
    public void logout() {

    }

    @Override
    public void addBook(String bookName) {

    }

    @Override
    public List<Book> listBooks() {
        return null;
    }

    @Override
    public void borrow(String bookName) {

    }

    @Override
    public void returnBook(String bookName) {

    }

    @Override
    public void waitlist(String bookName) {

    }

    @Override
    public void status() {

    }
}
