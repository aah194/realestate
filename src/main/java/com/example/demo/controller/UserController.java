package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists.");
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Note: Hashing passwords is strongly recommended
        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) { // Plaintext password check
            model.addAttribute("user", user);
            return "profile";
        }

        model.addAttribute("error", "Invalid username or password.");
        return "login";
    }

    @GetMapping("/profile")
    public String viewProfile(@RequestParam("username") String username, Model model) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
            return "profile";
        }

        model.addAttribute("error", "User not found.");
        return "login";
    }

    @GetMapping("/edit-profile")
    public String showEditProfileForm(@RequestParam("username") String username, Model model) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
            return "edit-profile";
        } else {
            // Redirect to login if user is not found
            return "redirect:/login";
        }
    }

    @PostMapping("/edit-profile")
    public String updateProfile(@RequestParam("oldUsername") String oldUsername,
                                @RequestParam("newUsername") String newUsername,
                                @RequestParam("password") String password,
                                Model model) {
        User user = userRepository.findByUsername(oldUsername);
        if (user != null) {
            if (userRepository.findByUsername(newUsername) == null || newUsername.equals(oldUsername)) {
                user.setUsername(newUsername);
                user.setPassword(password); // In a real application, use a password encoder
                userRepository.save(user);
                model.addAttribute("user", user);
                return "profile";
            } else {
                model.addAttribute("error", "New username is already taken.");
                model.addAttribute("user", user);
                return "edit-profile";
            }
        }
        // Redirect to login if user is not found
        return "redirect:/login";
    }


}
