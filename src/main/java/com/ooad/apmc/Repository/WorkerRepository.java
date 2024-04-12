package com.ooad.apmc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ooad.apmc.Models.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}