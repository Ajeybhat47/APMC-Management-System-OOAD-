package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ooad.apmc.DTOModels.BidDTO;
import com.ooad.apmc.Models.Bid;
import com.ooad.apmc.Service.AuctionService;
import com.ooad.apmc.Service.BidService;
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

    @Autowired AuctionService auctionService;
    
    @GetMapping
    public String getMethodName() {
        return "bid"; // Thymeleaf template name for bid landing page
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
            // Assuming userId is a Long in the model
            model.addAttribute("userId", String.valueOf(traderId));

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
