package com.ooad.apmc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ooad.apmc.Models.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findBySeller_UserId(Long sellerId);
}