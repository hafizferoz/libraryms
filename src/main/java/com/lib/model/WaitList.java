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

        @Column(name = "postiion")
        private int position;



}
