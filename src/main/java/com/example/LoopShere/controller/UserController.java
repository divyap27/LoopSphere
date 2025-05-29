package com.example.LoopShere.controller;

import com.example.LoopShere.model.User;
import com.example.LoopShere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    
    @PostMapping("/api/users/signup")
    public String registerUserViaForm(@ModelAttribute User user, Model model) {
        userService.saveUser(user);
        model.addAttribute("message", "Signup successful. Please login.");
        return "redirect:/login.html"; 
    }

    
    @PostMapping("/api/users/login")
    public String loginUserViaForm(@RequestParam String email, @RequestParam String password, Model model) {
        String result = userService.login(email, password);
        if ("Login successful".equals(result)) {
            return "redirect:/welcome.html"; 
        } else {
            model.addAttribute("error", result);
            return "redirect:/login.html"; 
        }
    }

    
    @RestController
    @RequestMapping("/api/json/users")
    public static class JsonApiController {

        @Autowired
        private UserService userService;

        @PostMapping("/signup")
        public User registerUser(@RequestBody User user) {
            return userService.saveUser(user);
        }

        @PostMapping("/login")
        public String loginUser(@RequestBody User loginData) {
            return userService.login(loginData.getEmail(), loginData.getPassword());
        }

        @GetMapping("/{email}")
        public User getUserByEmail(@PathVariable String email) {
            return userService.getUserByEmail(email).orElse(null);
        }
    }
}
