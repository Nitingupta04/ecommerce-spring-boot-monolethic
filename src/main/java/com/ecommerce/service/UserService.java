package com.ecommerce.service;

import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for User entities. Contains business logic for
 * registering new users, authenticating existing users and managing
 * user profiles.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieve all users.
     *
     * @return list of users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Find a user by ID.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Register a new user. Ensures that the username and email are unique.
     *
     * @param user new user to register
     * @return the saved user
     * @throws RuntimeException if username or email already exists
     */
    public User registerUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username is required");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }
        
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    /**
     * Authenticate a user by username or email and password.
     *
     * @param usernameOrEmail username or email
     * @param password        password
     * @return the authenticated user
     * @throws RuntimeException if authentication fails
     */
    public User login(String usernameOrEmail, String password) {
        if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty()) {
            throw new RuntimeException("Username or email is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }
        
        Optional<User> userOpt = userRepository.findByUsername(usernameOrEmail);
        if (!userOpt.isPresent()) {
            userOpt = userRepository.findByEmail(usernameOrEmail);
        }
        User user = userOpt.orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

    /**
     * Update an existing user's profile. Only simple fields are updated.
     *
     * @param id   the ID of the user to update
     * @param data user details to update
     * @return the updated user
     * @throws RuntimeException if the user does not exist
     */
    public User updateUser(Long id, User data) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(data.getUsername());
            user.setEmail(data.getEmail());
            user.setPassword(data.getPassword());
            user.setFullName(data.getFullName());
            user.setPhoneNumber(data.getPhoneNumber());
            user.setAddress(data.getAddress());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Delete a user by ID.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
