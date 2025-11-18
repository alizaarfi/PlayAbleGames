package com.example.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
import jakarta.inject.Singleton;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Singleton
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //register new user
    public User register (String username, String email, String rawPassword){
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }
        String encrypted= BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        User u= new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(encrypted);
        u.setEnabled(true);

        return userRepository.save(u);
    }
}
