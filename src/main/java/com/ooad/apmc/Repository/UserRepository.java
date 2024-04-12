package com.ooad.apmc.Repository;
// UserRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

import com.ooad.apmc.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

