package com.example.service;

import com.example.domain.User;
import com.example.domain.Role;
import com.example.domain.UserRole;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.repository.UserRoleRepository;
import jakarta.inject.Singleton;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

/**
 * service handling user registration and authentication logic
 */

@Singleton
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
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

    //get all role names for a user
    public java.util.List<String> getUserRoles(Long userID) {
        java.util.List<UserRole> userRoles = userRoleRepository.findByUserId(userID);

        return userRoles.stream()
                .map(ur -> roleRepository.findById(ur.getRoleId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Role::getName)
                .collect(java.util.stream.Collectors.toList());
    }

    // check if user is an admin
    public boolean isAdmin(Long userID) {
        java.util.List<String> roles=getUserRoles(userID);
        return roles.contains("ADMIN");
    }
}
