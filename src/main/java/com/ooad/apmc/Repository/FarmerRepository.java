package com.ooad.apmc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.ooad.apmc.Models.Farmer;
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Optional<Farmer> findByUsername(String username);
}