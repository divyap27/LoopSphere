package com.example.LoopShere.service;

import com.example.LoopShere.model.User;
import com.example.LoopShere.repository.UserRepository;
import com.example.LoopShere.service.exception.UserAlreadyExistsException;
import com.example.LoopShere.service.exception.UserAlreadyExistsException; 

import lombok.RequiredArgsConstructor;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServic { 

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public User saveUser(User user) {
        logger.info("Saving user with email: {}", user.getEmail());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists: " + user.getEmail());
        }
        if (userRepository.findByPhone(user.getPhone()).isPresent()) {
            throw new UserAlreadyExistsException("Phone already exists: " + user.getPhone());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        logger.info("User saved with ID: {}", savedUser.getId());
        return savedUser;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        logger.info("Fetching user by email: {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        logger.info("Fetching user by phone: {}", phone);
        return userRepository.findByPhone(phone);
    }

    @Override
    public Optional<User> findById(Long id) {
        logger.info("Fetching user by ID: {}", id);
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        logger.info("Fetching user by email (getUserByEmail): {}", email);
        return findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        logger.info("Deleting user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new Exception("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public String login(String email, String password) {
        logger.info("Attempting login for email: {}", email);
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            logger.info("Login successful for email: {}", email);
            return "Login successful";
        } catch (AuthenticationException e) {
            logger.error("Login failed for email: {} - {}", email, e.getMessage());
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    @Override
    public String logout() {
        logger.info("User logged out");
        return "Logout successful";
    }

    public boolean validateUser(String email, String password) {
    logger.info("Validating user for email: {}", email);
    if (email == null || password == null) {
        logger.error("Email or password is null");
        return false;
    }
    try {
        org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email.trim(), password)
        );
        logger.info("Validation successful for email: {}", email);
        return authentication.isAuthenticated();
    } catch (AuthenticationException e) {
        logger.error("Authentication failed for email: {} - {}", email, e.getMessage());
        return false;
    } catch (Exception e) {
        logger.error("Unexpected error during authentication for email: {} - {}", email, e.getMessage());
        return false;
    }
}
}
