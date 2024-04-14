package com.ooad.apmc.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooad.apmc.Service.*;
import com.ooad.apmc.Models.*;
import com.ooad.apmc.Models.Farmer;
import com.ooad.apmc.Repository.FarmerRepository;
import com.ooad.apmc.Repository.ItemRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import java.util.List;
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private BidService bidService;

    public Item createItem(Item item, Long farmerId) {
        try {
            if (item == null) {
                throw new IllegalArgumentException("Item cannot be null");
            }

            if (farmerId == null) {
                throw new IllegalArgumentException("Farmer ID cannot be null");
            }

            Farmer farmer = farmerRepository.findById(farmerId)
                    .orElseThrow(() -> new NoSuchElementException("Farmer not found"));

            item.setSeller(farmer);
            Item savedItem = itemRepository.save(item);

            // Create a bid with a value of 0
            Bid bid = new Bid();
            bid.setItem(savedItem);
            bid.setBidAmount(0.0);
            bidService.saveBid(bid);

            return savedItem;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating item: " + e.getMessage(), e);
        }
    }
    // public List<Item> getItemsByUser(Long userId) {
    //     Optional<Farmer> farmer = farmerRepository.findById(userId);
    //     if (farmer.isPresent()) {
    //         return itemRepository.findByFarmer(farmer.get());
    //     } else {
    //         throw new NoSuchElementException("No farmer found with id " + userId);
    //     }
    // }
    public List<Item> getItemsByUser(Long userId) {
        return itemRepository.findBySeller_UserId(userId);
    }

    public Item getItemById(Long itemId) {
        try {
            Optional<Item> item = itemRepository.findById(itemId);
            return item.orElseThrow(() -> new NoSuchElementException("Item not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching item: " + e.getMessage(), e);
        }
    }

    public String deleteItem(Long itemId) {
        try {
            if (itemRepository.existsById(itemId)) {
                itemRepository.deleteById(itemId);
                return "Item deleted successfully";
            } else {
                return "Item not found";
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting item: " + e.getMessage(), e);
        }
    }
}
