package com.lib.service;

import com.lib.model.User;
import com.lib.model.WaitList;
import com.lib.repo.WaitListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaitListService {
    @Autowired
    private WaitListRepository waitlistRepository;

    public List<WaitList> findWaitListByUser(User user) {
        return  waitlistRepository.findByUser(user);
    }
}
