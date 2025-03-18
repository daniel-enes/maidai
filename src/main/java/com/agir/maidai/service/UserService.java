package com.agir.maidai.service;

import com.agir.maidai.entity.User;
import com.agir.maidai.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {

        user.setActive(true);
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        //user.setPassword();

        User createdUser = userRepository.save(user);

        return createdUser;
    }

    public Optional<User> getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
