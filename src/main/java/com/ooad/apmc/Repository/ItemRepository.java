package com.ooad.apmc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ooad.apmc.Models.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}