package com.example.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
import jakarta.inject.Singleton;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

@Singleton
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //register new user
    public User register (String username, String email, String rawPassword){
        Optional<User> existingUser = userRepository.findByUsername(username);
        Optional<User> existingEmail = userRepository.findByEmail(email);

        if (existingEmail.isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        if (existingUser.isPresent()) {
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

    public Optional<User> authenticate(String username, String rawPassword){
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) return Optional.empty();

        boolean matches = BCrypt.checkpw(rawPassword, user.get().getPassword());

        return matches ? user : Optional.empty();
    }
}
