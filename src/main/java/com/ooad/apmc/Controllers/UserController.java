package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ooad.apmc.Models.Farmer;
import com.ooad.apmc.Models.Trader;
import com.ooad.apmc.Models.Worker;
import com.ooad.apmc.Service.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createWorker")
    public ResponseEntity<String> createWorker(@RequestBody Worker user) {
        try {
            userService.createWorker(user);
            return ResponseEntity.ok("Worker created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/createTrader")
    public ResponseEntity<String> createTrader(@RequestBody Trader trader) {
        try {
            userService.createTrader(trader);
            return ResponseEntity.ok("Trader created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/createFarmer")
    public ResponseEntity<String> createFarmer(@RequestBody Farmer user) {
        try {
            userService.createFarmer(user);
            return ResponseEntity.ok("Farmer created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getWorkerById")
    public ResponseEntity<?> getWorkerById(@RequestParam("workerId") Long workerId) {
        Worker worker = userService.getWorkerById(workerId);
        if (worker != null) {
            return ResponseEntity.ok(worker);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getTraderById")
    public ResponseEntity<?> getTraderById(@RequestParam("traderId") Long traderId) {
        Trader trader = userService.getTraderById(traderId);
        if (trader != null) {
            return ResponseEntity.ok(trader);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getFarmerById")
    public ResponseEntity<?> getFarmerById(@RequestParam("farmerId") Long farmerId) {
        Farmer farmer = userService.getFarmerById(farmerId);
        if (farmer != null) {
            return ResponseEntity.ok(farmer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
