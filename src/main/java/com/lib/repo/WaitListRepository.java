package com.lib.repo;

import com.lib.model.Book;
import com.lib.model.User;
import com.lib.model.WaitList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaitListRepository extends JpaRepository<WaitList, Long> {
    List<WaitList> findByBookOrderByPositionAsc(Book book);
    List<WaitList> findByUser(User user);
    boolean existsByBookAndUser(Book book, User user);
}
