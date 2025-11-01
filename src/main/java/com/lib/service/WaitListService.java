package com.lib.service;

import com.lib.model.Book;
import com.lib.model.User;
import com.lib.model.WaitList;
import com.lib.repo.WaitListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class WaitListService {
    @Autowired
    private WaitListRepository waitlistRepository;

    public List<WaitList> findWaitListByUser(User user) {
        return  waitlistRepository.findByUser(user);
    }

    public boolean waitlistExistsByBookAndUser(Book book, User user) {
        return waitlistRepository.existsByBookAndUser(book,user);
    }

    public Collection<WaitList> findWaitlistByBookOrderAndPosition(Book book) {
        return waitlistRepository.findByBookOrderByPositionAsc(book);
    }

    public void save(WaitList waitlist) {
        waitlistRepository.save(waitlist);
    }
}
