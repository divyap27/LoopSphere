package com.example.LoopShere.service;

import com.example.LoopShere.model.User;
import java.util.List;
import java.util.Optional;

public interface UserServic {
User saveUser(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Optional<User> findById(Long id);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(Long id) throws Exception;
    String login(String email, String password);
    String logout();

}
