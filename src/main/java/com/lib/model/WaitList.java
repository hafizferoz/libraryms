package com.lib.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "wait_list")
public class WaitList implements Serializable {

        @Id
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_name", referencedColumnName = "user_name")
        private User user;

        @ManyToOne
        @JoinColumn(name = "book_name", referencedColumnName = "book_name")
        private Book book;

        @Column(name = "position")
        private int position;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }

        public Book getBook() {
                return book;
        }

        public void setBook(Book book) {
                this.book = book;
        }

        public int getPosition() {
                return position;
        }

        public void setPosition(int position) {
                this.position = position;
        }
}
