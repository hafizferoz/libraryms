package com.lib.service;

import com.lib.model.User;
import com.lib.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> findUser(String username){
        return Optional.of(userRepository.findById(username).orElseGet(() -> userRepository.save(new User(username))));
    }

}
