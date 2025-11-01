package com.lib.service;

import com.lib.model.Book;
import com.lib.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        if(isAdmin()){
            System.out.println("Hello, admin!\nYou have access to library management.");
        }else{
            System.out.printf("Hello, %s!\n", user);
        }

    }

    private boolean isAdmin() {
        String username = loggedUser.get().getUsername();
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
        if(verifyIsUserLogin()) return;
        if (!isAdmin()) { System.out.println("Only admin can add books."); return; }
        if (bookService.bookExists(bookName)) {
            System.out.println("Book \"" + bookName + "\" already exists."); return;
        }
        bookService.save(new Book(bookName));
        System.out.println("Book \"" + bookName + "\" has been added to the library.");
    }

    @Override
    public void listBooks() {
        if (verifyIsUserLogin()) return;
        List<Book> books = bookService.findAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books are registered.");
            return;
        }
        AtomicInteger sno = new AtomicInteger(1);
        books.forEach(book -> {
            String borrowed = book.isBorrowed() ?  "borrowed" : "available";
            System.out.printf("%d. %s (%s)\n",sno.getAndIncrement() , book.getBookName(),borrowed );
        });
    }

    @Override
    public void borrow(String bookName) {
        if (verifyIsUserLogin()) return;
        Optional<Book> book = bookService.findBookById(bookName);
        if (!book.isPresent())
            System.out.printf("Sorry, \"%s\" is not registered.\n", bookName);
        Book borrowedBook = book.get();
        if (!borrowedBook.isBorrowed()) {
            borrowedBook.setBorrowed(true);
            borrowedBook.setBorrowedBy(loggedUser.get());
            bookService.save(borrowedBook);
            System.out.printf("You borrowed %s\n", bookName);
        } else {
            System.out.printf("Sorry, \"%s\" is currently not available.\n", bookName);
        }
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
