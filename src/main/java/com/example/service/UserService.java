package com.example.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
import jakarta.inject.Singleton;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Map;
import java.util.Optional;

/**
 * service handling user registration and authentication logic
 */

@Singleton
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * register a new user with encrypted password
     *
     * @param username desired username
     * @param email user's email address
     * @param rawPassword plaintext password
     * @return the saved user object
     */
    public User register (String username, String email, String rawPassword){
        Optional<User> existingUser = userRepository.findByUsername(username);
        Optional<User> existingEmail = userRepository.findByEmail(email);

        if (existingEmail.isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        //encrypt password using bcrypt
        String encrypted= BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        User u= new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(encrypted);
        u.setEnabled(true);

        return userRepository.save(u);
    }

    /**
     * authenticate a user by username and password
     * @param username Username
     * @param rawPassword plaintext password
     * @return Optional<User> if authentication succeeds
     */
    public Optional<User> authenticate(String username, String rawPassword){
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) return Optional.empty();

        //compare password with hashed password
        boolean matches = BCrypt.checkpw(rawPassword, user.get().getPassword());

        if (!matches){
            throw new RuntimeException("Invalid password");
        }
        return user;
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void changePassword(String username, String oldPassword, String newPassword) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // verify old password
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        // encrypt new password
        String encrypted = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPassword(encrypted);

        userRepository.update(user);
    }


}
