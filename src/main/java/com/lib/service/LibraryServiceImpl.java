package com.lib.service;

import com.lib.model.Book;
import com.lib.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LibraryServiceImpl implements LibraryService{
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    Optional<User> loggedUser;

    @Override
    public void login(String username) {

        loggedUser = userService.findUser(username);
        if(loggedUser.isPresent() && loggedUser.get().getUsername().equalsIgnoreCase("admin")){
            System.out.println("Hello, admin!\nYou have access to library management.\n");
        }

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
