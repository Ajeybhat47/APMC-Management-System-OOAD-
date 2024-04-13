package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


import com.ooad.apmc.DTOModels.UserDTO;
import com.ooad.apmc.Models.User;
import com.ooad.apmc.Service.UserService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/")

public class LoginController{

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
    // @GetMapping("/home")
    // public String home() {
    //     return "home";
    // }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            User user = userService.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                model.addAttribute("user", user);
                return "home";
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error",e.getMessage());
            return "login";
        }
    }

}
