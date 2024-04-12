package com.ooad.apmc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ooad.apmc.Models.Trader;

public interface TraderRepository extends JpaRepository<Trader, Long> {

}
