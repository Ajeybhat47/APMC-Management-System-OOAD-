package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ooad.apmc.DTOModels.BidDTO;
import com.ooad.apmc.Models.Bid;
import com.ooad.apmc.Models.Auction;

import com.ooad.apmc.DTOModels.AuctionDTO;
import com.ooad.apmc.Models.Notification;
import com.ooad.apmc.Service.AuctionService;
import com.ooad.apmc.Service.BidService;
import com.ooad.apmc.Service.NotificationService;
import com.ooad.apmc.Service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/auction/bid")
public class BidController {

    @Autowired
    private BidService bidService;


    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuctionService auctionService;

    @GetMapping
    public String getMethodName() {
        return "trader/bid"; // Thymeleaf template name for bid landing page
    }

// Controller Method
    @GetMapping("/addBidForm")
    public String addBidForm(@RequestParam("userId") Long userId, @RequestParam("auctionId") Long auctionId, Model model) {
        // Fetch user and auction details

        // Add user, auction, and a new Bid object to the Model
        model.addAttribute("userId", userId);
        model.addAttribute("auctionId", auctionId);
        model.addAttribute("bid", new Bid()); // Assuming Bid is a DTO or form backing object

        // Return the name of the Thymeleaf template
        return "trader/addBidForm";
    }

    @PostMapping("/addBid")
    public String addBid(@ModelAttribute("bid") Bid bid, @RequestParam(value = "auctionId") Long auctionId, @RequestParam(value = "traderId") Long traderId, Model model) {
        try {
            String result = bidService.addBid(bid, auctionId, traderId);
            model.addAttribute("result", result);
            model.addAttribute("userId", String.valueOf(traderId));
            String message = "A bid of " + bid.getBidAmount() + " has been placed by Trader ID: " + traderId + " for Auction ID: " + auctionId;
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setStatus("pending");

            AuctionDTO auction = auctionService.getAuctionById(auctionId);
            notification.setUser(auction.getSeller());
            notificationService.createNotification(notification);

            model.addAttribute("userType", "trader");
            return "trader/bidAdded"; // Thymeleaf template name for success message
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }

    @GetMapping("/getBidByTraderId")
    public String getBidByTraderId(@RequestParam("traderId") Long traderId, @RequestParam("auctionId") Long auctionId, Model model) {
        BidDTO bid = bidService.getBidByTraderId(traderId, auctionId);
        if (bid != null) {
            model.addAttribute("bid", bid);
            return "bidDetails"; // Thymeleaf template name for displaying bid details
        } else {
            model.addAttribute("error", "Bid not found");
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }

    @GetMapping("/getAllBidByTraderId")
    public String getAllBidByTraderId(@RequestParam("traderId") Long traderId, Model model) {
        model.addAttribute("bids", bidService.getAllBidsByTrader(traderId));
        return "allBids"; // Thymeleaf template name for displaying all bids by a trader
    }

    @PutMapping("/updateBid")
    public String updateBid(@RequestParam("bidId") Long bidId, @RequestParam("price") Double price, Model model) {
        try {
            bidService.updateBid(bidId, price);
            model.addAttribute("result", "Bid updated successfully");
            return "bidUpdated"; // Thymeleaf template name for success message
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }



}
