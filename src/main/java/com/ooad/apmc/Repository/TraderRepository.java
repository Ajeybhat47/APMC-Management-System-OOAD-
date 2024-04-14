package com.ooad.apmc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ooad.apmc.Models.Trader;

import java.util.Optional;


public interface TraderRepository extends JpaRepository<Trader, Long> {
    Optional<Trader> findByUsername(String username);
}