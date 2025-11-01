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
    public void login(String user) {
        if(loggedUser!=null && loggedUser.isPresent()){
            System.out.println("You're alreadty logged in!");
            return;
        }
        loggedUser = userService.findUser(user);
        String username = loggedUser.get().getUsername();
        if(loggedUser.isPresent() && username.equalsIgnoreCase("admin")){
            System.out.println("Hello, admin!\nYou have access to library management.\n");
        }else{
            System.out.printf("Hello, %s!", username);
        }

    }

    @Override
    public void logout() {
        if(loggedUser==null || !loggedUser.isPresent()){
            System.out.println("You're not logged in!");
            return;
        }
        String username = loggedUser.get().getUsername();
        System.out.printf("Goodbye, %s!\n", username);
        loggedUser=null;
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
