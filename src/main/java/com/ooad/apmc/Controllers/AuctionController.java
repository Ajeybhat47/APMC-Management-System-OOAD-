package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ooad.apmc.DTOModels.*;
import com.ooad.apmc.Models.Auction;
import com.ooad.apmc.Service.AuctionService;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;


    @GetMapping
    public String landingPage() {
        return "auction"; // Thymeleaf template name
    }

    @GetMapping("/createAuction")
    public String getMethodName() {
        return "createAuction"; // Thymeleaf template name
    }
    

    @PostMapping("/createAuction")
    public String createAuction(@ModelAttribute("auction") Auction auction, @RequestParam("itemId") Long itemId, Model model) {
        try {
            String result = auctionService.createAuction(auction, itemId);
            model.addAttribute("result", result);
            return "auctionCreated"; // Thymeleaf template name
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create auction: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }

    @GetMapping("/{auctionId}/getAllBids")
    public String getAllBids(@PathVariable Long auctionId, Model model) {
        try {
            List<BidDTO> bids = auctionService.getAllBids(auctionId);
            model.addAttribute("bids", bids);
            return "bidsList"; // Thymeleaf template name
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve bids: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }

    @GetMapping("/getAllActiveAuctions")
    public String getAllActiveAuctions(Model model) {
        try {
            List<AuctionDTO> auctions = auctionService.getAllAuctionsByStatus("active");
            model.addAttribute("auctions", auctions);
            return "activeAuctions"; // Thymeleaf template name
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve active auctions: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }

    @GetMapping("/getAllAuctions")
    public String getAllAuctions(Model model) {
        try {
            List<AuctionDTO> auctions = auctionService.getAllAuctions();
            model.addAttribute("auctions", auctions);
            return "allAuctions"; // Thymeleaf template name
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve auctions: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }

    @GetMapping("/getAllClosedAuctions")
    public String getAllClosedAuctions(Model model) {
        try {
            List<AuctionDTO> auctions = auctionService.getAllAuctionsByStatus("closed");
            model.addAttribute("auctions", auctions);
            return "closedAuctions"; // Thymeleaf template name
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve closed auctions: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }
    // get auction by id
    @GetMapping("/getAuction")
    public String getAuction(@RequestParam Long auctionId, Model model) {
        try {
            AuctionDTO auction = auctionService.getAuctionById(auctionId);
            if (auction != null) {
                model.addAttribute("auction", auction);
                return "auctionDetails"; // Thymeleaf template name
            } else {
                model.addAttribute("error", "Auction not found with ID " + auctionId);
                return "errorPage"; // Thymeleaf template name for error handling
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve auction: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }
    

    @PostMapping("/closeAuction")
    public String closeAuction(@RequestParam Long auctionId, Model model) {
        try {
            String result = auctionService.closeAuction(auctionId);
            model.addAttribute("result", result);
            return "auctionClosed"; // Thymeleaf template name
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (Exception e) {
            model.addAttribute("error", "Failed to close auction: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }

    @GetMapping("/{auctionId}/getWinner")
    public String getWinner(@PathVariable Long auctionId, Model model) {
        try {
            UserDTO winner = auctionService.getWinner(auctionId);
            if (winner != null) {
                model.addAttribute("winner", winner);
                return "winnerPage"; // Thymeleaf template name
            } else {
                model.addAttribute("error", "Winner not found for auction with ID " + auctionId);
                return "errorPage"; // Thymeleaf template name for error handling
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve winner: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }
}
