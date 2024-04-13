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
}
