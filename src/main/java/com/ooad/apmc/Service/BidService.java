package com.ooad.apmc.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ooad.apmc.DTOModels.BidDTO;
import com.ooad.apmc.Models.Auction;
import com.ooad.apmc.Models.Item;
import com.ooad.apmc.Models.Bid;

import com.ooad.apmc.Repository.AuctionRepository;
import com.ooad.apmc.Repository.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.ooad.apmc.Models.Trader;

import com.ooad.apmc.Repository.TraderRepository;
@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private ItemRepository itemRepository;

    public BidDTO getBidById(Long bidId) {
        Bid bid = bidRepository.findById(bidId).orElseThrow(() -> new NoSuchElementException("Bid not found for ID: " + bidId));
        return new BidDTO(bid);
    }

    public List<BidDTO> getAllBids(Long auctionId) {
        List<Bid> bids = bidRepository.findByAuctionId(auctionId);
        return bids.stream().map(BidDTO::new).collect(Collectors.toList());
    }

    public BidDTO getBidByTraderId(Long traderId, Long auctionId) {
        Bid bid = bidRepository.findByBidderId(traderId, auctionId);
        if (bid == null) {
            return null;
        }
        return new BidDTO(bid);
    }

    public List<BidDTO> getAllBidsByTrader(Long traderId) {
        Trader trader = traderRepository.findById(traderId).orElseThrow(() -> new NoSuchElementException("Trader not found for ID: " + traderId));

        List<Bid> bids = trader.getBids();
        return bids.stream().map(BidDTO::new).collect(Collectors.toList());
    }

    public String addBid(Bid bid, Long auctionId, Long traderId) {
        if (auctionId == null || traderId == null || bid == null) {
            throw new IllegalArgumentException("Auction ID, Trader ID, and Bid information must not be null.");
        }

        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new NoSuchElementException("Auction not found for ID: " + auctionId));
        Trader trader = traderRepository.findById(traderId).orElseThrow(() -> new NoSuchElementException("Trader not found for ID: " + traderId));

        if (getBidByTraderId(traderId, auctionId) != null) {
            throw new IllegalArgumentException("Each trader can place only one bid per auction.");
        }

        bid.setBidTime(java.time.LocalDateTime.now());
        bid.setBidder(trader);
        bid.setAuction(auction);
        bid.setBidStatus("pending");

        bidRepository.save(bid);
        return "Bid has been added successfully";
    }

    public Bid saveBid(Bid bid) {
        try {
            return bidRepository.save(bid);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while saving bid: " + e.getMessage(), e);
        }
    }

    public Bid getHighestBidForItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new NoSuchElementException("Item not found for ID: " + itemId));

        return bidRepository.findByItemOrderByBidAmountDesc(item)
            .stream()
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("No bids found for item ID: " + itemId));
    }

    public Bid getHighestBidForAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
            .orElseThrow(() -> new NoSuchElementException("Auction not found for ID: " + auctionId));

        return bidRepository.findByAuctionOrderByBidAmountDesc(auction)
            .stream()
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("No bids found for auction ID: " + auctionId));
    }

    public void updateBid(Long bidId, Double price) {
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price must be a positive value.");
        }

        Bid bid = bidRepository.findById(bidId).orElseThrow(() -> new NoSuchElementException("Bid not found for ID: " + bidId));

        bid.setBidAmount(price);
        bidRepository.save(bid);
    }
}
