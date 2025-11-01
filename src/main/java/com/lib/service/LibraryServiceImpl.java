package com.lib.service;

import com.lib.model.Book;
import com.lib.model.User;
import com.lib.model.WaitList;
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
    @Autowired
    private WaitListService waitListService;

    @Override
    public void login(String user) {
        if (verifyIsUserLogout()) return;
        loggedUser = userService.findUser(user);
        if(isAdmin()){
            System.out.println("Hello, admin!\nYou have access to library management.");
        }else{
            System.out.printf("Hello, %s!\n", user);
            status();
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
        if (!verifyIsUserLogin()) return;
        String username = loggedUser.get().getUsername();
        System.out.printf("Goodbye, %s!\n", username);
        loggedUser=null;
    }

    private boolean verifyIsUserLogin() {
        if(loggedUser==null || !loggedUser.isPresent()){
            System.out.println("You're not logged in!");
            return false;
        }
        return true;
    }

    @Override
    public void addBook(String bookName) {
        if(!verifyIsUserLogin()) return;
        if (!isAdmin()) { System.out.println("Only admin can add books."); return; }
        if (bookService.bookExists(bookName)) {
            System.out.println("Book \"" + bookName + "\" already exists."); return;
        }
        bookService.save(new Book(bookName));
        System.out.println("Book \"" + bookName + "\" has been added to the library.");
    }

    @Override
    public void listBooks() {
        if (!verifyIsUserLogin()) return;
        List<Book> books = bookService.findAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books are registered");
            return;
        }
        AtomicInteger sno = new AtomicInteger(1);
        books.forEach(book -> {
            String borrowed = book.isBorrowed() || book.getBorrowedBy()!=null?  "borrowed" : "available";
            System.out.printf("%d. %s (%s)\n",sno.getAndIncrement() , book.getBookName(),borrowed );
        });
    }

    @Override
    public void borrow(String bookName) {
        if (!verifyIsUserLogin()) return;
        Book borrowedBook = bookService.findBookById(bookName).orElse(null);
        if (borrowedBook ==null) {
            System.out.printf("Sorry, \"%s\" is not registered.\n", bookName);
            return;
        }
        if (!borrowedBook.isBorrowed()) {
            borrowedBook.setBorrowed(true);
            borrowedBook.setBorrowedBy(loggedUser.get());
            bookService.save(borrowedBook);
            System.out.printf("You borrowed %s.\n", bookName);
        } else {
            System.out.printf("Sorry, \"%s\" is currently not available.\n", bookName);
        }
    }

    @Override
    public void returnBook(String bookName) {
        if (!verifyIsUserLogin()) return;
        Optional<Book> book = bookService.findBookById(bookName);
        if (!book.isPresent()) {
            System.out.printf("Sorry, \"%s\" is not registered.\n",bookName);
            return;
        }
        Book borrowedBook = book.get();
        if (borrowedBook.getBorrowedBy() == null || !borrowedBook.getBorrowedBy().equals(loggedUser.get())) {
            System.out.printf("Sorry, you didn't borrow \"%s\".\n",bookName);
            return;
        }

        borrowedBook.setBorrowedBy(null);
        borrowedBook.setBorrowed(false);
        bookService.save(borrowedBook);
        System.out.printf("You returned \"%s\".\n",bookName);

        List<WaitList> waitlist = waitListService.findWaitlistByBookOrderAndPosition(book.get());
        if (!waitlist.isEmpty()) {
            WaitList first = waitlist.remove(0);
            User next = first.getUser();
            book.get().setBorrowedBy(next);
            bookService.save(book.get());
            waitListService.delete(first);
            waitlist.forEach(wl -> {
                wl.setPosition(wl.getPosition()-1);
            });
            waitListService.saveBatch(waitlist);
            //System.out.println("User " + next.getUsername()+ " got the book \"" + bookName + "\" from their waitlist");
        }
    }

    @Override
    public void waitlist(String bookName) {
        if (!verifyIsUserLogin()) return;
        Book book = bookService.findBookById(bookName).orElse(null);
        if (book == null) {
            System.out.println("Sorry, \"" + bookName + "\" is not registered.");
            return;
        }
        if (!book.isBorrowed()) {
            System.out.println("Book \"" + bookName + "\" is available. You can borrow it directly.");
            return;
        }
        if (waitListService.waitlistExistsByBookAndUser(book, loggedUser.get())) {
            System.out.println("You are already in the waitlist of \"" + bookName + "\".");
            return;
        }

        int position = waitListService.findWaitlistByBookOrderAndPosition(book).size() + 1;
        waitListService.save(new WaitList(book, loggedUser.get(), position));
        System.out.println("You are added to the wait list of \"" + bookName + "\", your position is " + position + ".");
    }

    @Override
    public void status() {
        if (!verifyIsUserLogin()) return;
        List<Book> borrowed = bookService.findAllBooks();
        borrowed.removeIf(book -> book.getBorrowedBy() == null || !book.getBorrowedBy().equals(loggedUser.get()));
        if (borrowed.isEmpty())
            System.out.println("You don't have any books borrowed yet.");
        else {

            borrowed.forEach(book -> {
                if(!book.isBorrowed() && book.getBorrowedBy().equals(loggedUser.get()))
                System.out.println("You got the book \"" + book.getBookName() + "\" from your waitlist");
                book.setBorrowed(true);
                bookService.save(book);
            });

            System.out.println("Your borrowed books:");
            AtomicInteger sno = new AtomicInteger(1);
            borrowed.forEach(book -> {
                System.out.println(sno.getAndIncrement() + ". " + book.getBookName());
            });

        }

        List<WaitList> waitlists = waitListService.findWaitListByUser(loggedUser.get());
        if (!waitlists.isEmpty()) {
            System.out.println("Your wait lists:");
            AtomicInteger sno = new AtomicInteger(1);
            waitlists.forEach(waitList -> {
                System.out.println(sno.getAndIncrement() + ". " + waitList.getBook().getBookName() + " - position " + waitList.getPosition());
            });
        }
    }
}
