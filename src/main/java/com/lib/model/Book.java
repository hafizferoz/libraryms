package com.lib.model;

import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @Column(name = "book_name")
    private String bookName;

    @Column(name = "is_borrowed")
    private boolean borrowed;
    @ManyToOne
    @JoinColumn(name = "borrowed_by", referencedColumnName = "user_name")
    private User borrowedBy;

    public Book(){}
    public Book(String bookName) {
        this.bookName=bookName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public User getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(User borrowedBy) {
        this.borrowedBy = borrowedBy;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        return StringUtils.equalsIgnoreCase(bookName, ((Book) o).bookName);
    }
    @Override
    public int hashCode() { return Objects.hash(bookName); }

}
