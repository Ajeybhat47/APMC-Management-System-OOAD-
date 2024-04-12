package com.ooad.apmc.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooad.apmc.DTOModels.UserDTO;
import com.ooad.apmc.Models.Trader;
import com.ooad.apmc.Models.User;
import com.ooad.apmc.Models.Worker;
import com.ooad.apmc.Models.Farmer;
import com.ooad.apmc.Repository.FarmerRepository;
import com.ooad.apmc.Repository.TraderRepository;
import com.ooad.apmc.Repository.UserRepository;
import com.ooad.apmc.Repository.WorkerRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private WorkerRepository workerRepository;



    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream().map(UserDTO::mapEntityToDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching all users: " + e.getMessage(), e);
        }
    }

    public User getUserById(Long id) {
        try {
            return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching user by ID: " + e.getMessage(), e);
        }
    }

    public Worker getWorkerById(Long id) {
        try {
            return workerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Worker not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching worker by ID: " + e.getMessage(), e);
        }
    }

    public Trader getTraderById(Long id) {
        try {
            return traderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Trader not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching trader by ID: " + e.getMessage(), e);
        }
    }

    public Farmer getFarmerById(Long id) {
        try {
            return farmerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Farmer not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching farmer by ID: " + e.getMessage(), e);
        }
    }


    public UserDTO getUserDTOById(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
            return new UserDTO(user);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching user DTO by ID: " + e.getMessage(), e);
        }
    }

    public void createUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating user: " + e.getMessage(), e);
        }
    }

    public void createWorker(Worker worker) {
        // Save Worker entity
        try {
            // Worker worker = new Worker(user);
            workerRepository.save(worker);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating user: " + e.getMessage(), e);
        }
    }

    public void createTrader(Trader trader) {
        // Save Trader entity
        try {
            // Trader trader = new Trader(user);
            traderRepository.save(trader);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating user: " + e.getMessage(), e);
        }

    }

    public void createFarmer(Farmer farmer) {
        // Save Farmer entity
        try {
            // Farmer farmer = new Farmer(farmer);
            farmerRepository.save(farmer);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating user: " + e.getMessage(), e);
        }
    }



    public void deleteUser(Long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
            } else {
                throw new NoSuchElementException("User not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting user: " + e.getMessage(), e);
        }
    }
}
