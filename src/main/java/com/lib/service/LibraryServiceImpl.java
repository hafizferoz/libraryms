package com.lib.service;

import com.lib.model.Book;
import com.lib.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
        if (verifyIsUserLogout()) return;
        loggedUser = userService.findUser(user);
        String username = loggedUser.get().getUsername();
        if(isAdmin(username)){
            System.out.println("Hello, admin!\nYou have access to library management.");
        }else{
            System.out.printf("Hello, %s!\n", username);
        }

    }

    private boolean isAdmin(String username) {
        return loggedUser.isPresent() && username.equalsIgnoreCase("admin");
    }

    private boolean verifyIsUserLogout() {
        if(loggedUser!=null && loggedUser.isPresent()){
            System.out.println("You're already logged in!");
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        if (verifyIsUserLogin()) return;
        String username = loggedUser.get().getUsername();
        System.out.printf("Goodbye, %s!\n", username);
        loggedUser=null;
    }

    private boolean verifyIsUserLogin() {
        if(loggedUser==null || !loggedUser.isPresent()){
            System.out.println("You're not logged in!");
            return true;
        }
        return false;
    }

    @Override
    public void addBook(String bookName) {

    }

    @Override
    public void listBooks() {
        if (verifyIsUserLogin()) return;
        List<Book> books = bookService.findAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books are registered");
            return;
        }
        AtomicInteger sno = new AtomicInteger(0);
        books.forEach(book -> {
            System.out.printf("%d. %s (%s)%n",sno.getAndIncrement() , book.getBookName(), book.isBorrowed() ? "available" : "borrowed");
        });
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
