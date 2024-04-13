package com.ooad.apmc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ooad.apmc.Models.APMC;

public interface APMCRepository extends JpaRepository<APMC, Long> {
}
