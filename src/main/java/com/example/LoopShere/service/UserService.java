package com.example.LoopShere.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.LoopShere.model.User;

import java.util.Optional;
import java.util.List;
 

public interface UserService {
    User saveUser(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Optional<User> findById(Long id);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(Long id);
    String login(String email, String password);
}
