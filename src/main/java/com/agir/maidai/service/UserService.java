package com.agir.maidai.service;

import com.agir.maidai.entity.User;
import com.agir.maidai.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Tente usar outro endereço de e-mail");
        }

        user.setActive(true);
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User createdUser = userRepository.save(user);

        return createdUser;
    }

    public Optional<User> getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    // New method to get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

}
