package com.ooad.apmc.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooad.apmc.DTOModels.AuctionDTO;
import com.ooad.apmc.DTOModels.BidDTO;

import com.ooad.apmc.Models.Trader;
import com.ooad.apmc.Models.Auction;
import com.ooad.apmc.Models.Bid;
import com.ooad.apmc.Repository.TraderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TraderService {
    @Autowired
    private TraderRepository traderRepository;


    public List<BidDTO> getHistory(Long userId) {
        try {
            Trader trader = traderRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Trader not found"));
            
            List<Bid> allBids = trader.getBids();
           
            return allBids.stream().map(BidDTO::mapEntityToDto).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching all bids: " + e.getMessage(), e);
        }
    }

    public List<AuctionDTO> getWonAuctions(Long userId) {
        try {
            Trader trader = traderRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Trader not found"));
            
            List<Auction> auctions = trader.getWonAuctions();

            return auctions.stream().map(AuctionDTO::mapEntityToDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching all bids: " + e.getMessage(), e);
        }
    }
}
