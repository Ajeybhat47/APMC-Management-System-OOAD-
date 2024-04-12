package com.ooad.apmc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ooad.apmc.Models.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {

}