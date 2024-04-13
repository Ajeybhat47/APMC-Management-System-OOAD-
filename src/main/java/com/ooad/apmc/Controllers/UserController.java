package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ooad.apmc.Models.Farmer;
import com.ooad.apmc.Models.Trader;
import com.ooad.apmc.Models.Worker;
import com.ooad.apmc.Service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/createWorker")
    public String showWorkerForm(Model model) {
        model.addAttribute("worker", new Worker());
        return "worker-form"; // Thymeleaf template
    }



    @PostMapping("/createWorker")
    public String createWorker(@ModelAttribute("worker") Worker worker, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Handle validation errors
            return "worker-form"; // Return back to the form with errors
        }
        try {
            userService.createWorker(worker);
            redirectAttributes.addFlashAttribute("successMessage", "Worker created successfully");
            return "redirect:/users/workers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error occurred: " + e.getMessage());
            return "redirect:/users/workers";
        }
    }

    // Similarly, update createTrader and createFarmer methods

    @GetMapping("/workers/{workerId}")
    public String getWorkerById(@PathVariable("workerId") Long workerId, Model model) {
        Worker worker = userService.getWorkerById(workerId);
        if (worker != null) {
            model.addAttribute("worker", worker);
            return "worker-details"; // Thymeleaf template
        } else {
            return "not-found"; // Thymeleaf template
        }
    }

    // Similarly, update getTraderById and getFarmerById methods

    @GetMapping("/trader/{traderId}")
    public String getTraderById(@PathVariable("traderId") Long traderId, Model model) {
        Trader trader = userService.getTraderById(traderId);
        if (trader != null) {
            model.addAttribute("trader", trader);
            return "trader-details"; // Thymeleaf template
        } else {
            return "not-found"; // Thymeleaf template
        }
    }

    @GetMapping("/farmer/{farmerId}")
    public String getFarmerById(@PathVariable("farmerId") Long farmerId, Model model) {
        Farmer farmer = userService.getFarmerById(farmerId);
        if (farmer != null) {
            model.addAttribute("farmer", farmer);
            return "farmer-details"; // Thymeleaf template
        } else {
            return "not-found"; // Thymeleaf template
        }
    }

    @PostMapping("/createTrader")
public String createTrader(@ModelAttribute("trader") Trader trader, BindingResult result, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
        // Handle validation errors
        return "trader-form"; // Return back to the form with errors
    }
    try {
        userService.createTrader(trader);
        redirectAttributes.addFlashAttribute("successMessage", "Trader created successfully");
        return "redirect:/users/traders";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Error occurred: " + e.getMessage());
        return "redirect:/users/traders";
    }
}

    @PostMapping("/createFarmer")
    public String createFarmer(@ModelAttribute("farmer") Farmer farmer, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Handle validation errors
            return "farmer-form"; // Return back to the form with errors
        }
        try {
            userService.createFarmer(farmer);
            redirectAttributes.addFlashAttribute("successMessage", "Farmer created successfully");
            return "redirect:/users/farmers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error occurred: " + e.getMessage());
            return "redirect:/users/farmers";
        }
    }

    
    
}
