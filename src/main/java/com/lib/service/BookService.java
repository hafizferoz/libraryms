package com.lib.service;

import com.lib.model.Book;
import com.lib.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    List<Book> findAllBooks(){
        return bookRepository.findAll();
    }
}
