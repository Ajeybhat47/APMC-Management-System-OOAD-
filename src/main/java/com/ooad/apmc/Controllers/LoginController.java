package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.ooad.apmc.Models.Worker;
import com.ooad.apmc.Models.Farmer;
import com.ooad.apmc.Models.Trader;

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

    @ModelAttribute("userDTO")
public UserDTO userDTO() {
    return new UserDTO();
}
    @GetMapping("/register")
    public String register() {
        return "register";
        }

        @PostMapping("/register")
        public String register(@ModelAttribute UserDTO userDto, @RequestParam String role, Model model) {
        try {
            User user;
            switch (role) {
            case "worker":
                Worker worker = new Worker();
                worker.setUsername(userDto.getUsername());
                worker.setPassword(userDto.getPassword());
                worker.setEmail(userDto.getEmail());

                userService.createWorker(worker);
                user = worker;
                break;
            case "farmer":
                Farmer farmer = new Farmer();
                farmer.setUsername(userDto.getUsername());
                farmer.setPassword(userDto.getPassword());
                farmer.setEmail(userDto.getEmail());
                userService.createFarmer(farmer);
                user = farmer;
                break;
            case "trader":
                Trader trader = new Trader();
                trader.setUsername(userDto.getUsername());
                trader.setPassword(userDto.getPassword());
                trader.setEmail(userDto.getEmail());
                userService.createTrader(trader);
                user = trader;
                break;
            default:
                model.addAttribute("error", "Invalid role");
                return "register";
            }
            model.addAttribute("user", user);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        }
        @GetMapping("/home")
        public String home() {
        return "home";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            User user = userService.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                model.addAttribute("user", user);
                return "redirect:/home";
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
