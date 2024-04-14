
package com.ooad.apmc.Service;

import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ooad.apmc.DTOModels.AuctionDTO;
import com.ooad.apmc.DTOModels.BidDTO;
import com.ooad.apmc.DTOModels.UserDTO;
import com.ooad.apmc.Models.Auction;
import com.ooad.apmc.Models.Bid;
import com.ooad.apmc.Models.Item;
import com.ooad.apmc.Models.*;

import com.ooad.apmc.Repository.AuctionRepository;
@Service
public class AuctionFacade {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private BidService bidService;

    @Autowired
    private NotificationService notificationService;

    // @Autowired
    public AuctionFacade(AuctionService auctionService, BidService bidService) {
        this.auctionService = auctionService;
        this.bidService = bidService;
    }

    public String closeAuction(Long itemId) {
        // Find the auction associated with the item
        Auction auction = auctionService.getAuctionByItemId(itemId);

        if (auction == null) {
            return "No auction found for this item.";
        }

        // Get the highest bid for the auction
        Bid highestBid = bidService.getHighestBidForAuction(auction.getAuctionId());

        // If there is a highest bid, close the auction
        if (highestBid != null) {
            String message = "Congratulations! You have won the auction for item ID: " + auction.getItem().getItemId();
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setUser(highestBid.getBidder());
            notificationService.createNotification(notification);

            String workerMessage = "Auction for item " + auction.getItem().getItemId() + " has ended. The seller's user ID is " + auction.getItem().getSeller().getUserId() + " and the trader's user ID is " + highestBid.getBidder().getUserId()+ ". Please move the item from the seller to the trader.";
            notificationService.notifyAllWorkers(workerMessage);

            return auctionService.closeAuction(auction.getAuctionId());
        } else {
            return "No bids placed for this auction.";
        }
    }
}