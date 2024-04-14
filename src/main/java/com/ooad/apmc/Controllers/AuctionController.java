package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ooad.apmc.DTOModels.*;
import com.ooad.apmc.Service.AuctionService;

import com.ooad.apmc.Models.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.ooad.apmc.Service.AuctionFacade;
import java.util.NoSuchElementException;
@Controller
@RequestMapping("/apmc/auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;


    @Autowired
    private AuctionFacade auctionFacade;
    @GetMapping("/endAuction/{itemId}")

    public String showEndAuctionPage(@PathVariable Long itemId, Model model) {
        // Retrieve the auction using the itemId
        Auction auction = auctionService.getAuctionByItemId(itemId);

        // Add the auction to the model
        model.addAttribute("auction", auction);

        // Return the name of the view that displays the end auction page
        return "item/itemList";
    }

    @PostMapping("/endAuction/{itemId}")
    public String endAuction(@PathVariable Long itemId, @RequestParam Long userId, Model model) {
        try {
            String result = auctionFacade.closeAuction(itemId);
            model.addAttribute("result", result);
            return "redirect:/item/item/" + userId;
        } catch (NoSuchElementException e) {
            model.addAttribute("message", "No bids found for this auction");
            return "redirect:/item/item/" + userId; // Thymeleaf template name for displaying the auction result
        }
    }

    @GetMapping
    public String auctionHandler(@RequestParam(value = "userType") String userType , @RequestParam(value = "userId") String userId,Model model) {

        model.addAttribute("userId", userId);
        model.addAttribute("userType", userType); // Add userType as an attribute

        return "auction"; // Thymeleaf template name
    }

    @GetMapping("/{auctionId}/getAllBids")
    public String getAllBids(@PathVariable(value = "auctionId") Long auctionId, Model model) {
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

    // get auction by id
    @GetMapping("/getAuction")
    public String getAuction(@RequestParam(value = "auctionId") Long auctionId,
                            @RequestParam(value = "userType") String userType,
                            @RequestParam(value = "userId") String userIdStr,
                            Model model) {
        try {
            Long userId = Long.parseLong(userIdStr); // Convert userId from String to Long
            AuctionDTO auction = auctionService.getAuctionById(auctionId);
            if (auction != null) {
                model.addAttribute("auction", auction);
                model.addAttribute("userId", userId);
                model.addAttribute("userType", userType);

                if (userType.equals("trader"))
                    return "trader/auctionDetails"; // Thymeleaf template name
                else
                    return "auctionDetails"; // Thymeleaf template name
            } else {
                model.addAttribute("error", "Auction not found with ID " + auctionId);
                return "errorPage"; // Thymeleaf template name for error handling
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid user ID format: " + userIdStr);
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve auction: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }

    @GetMapping("/farmer/getAuction")
    public String getFarmerAuction(@RequestParam(value = "auctionId") Long auctionId,Model model) {
        try {

            AuctionDTO auction = auctionService.getAuctionById(auctionId);
            if (auction != null) {
                model.addAttribute("auction", auction);
                return "auctionDetails"; // Thymeleaf template name
            } else {
                model.addAttribute("error", "Auction not found with ID " + auctionId);
                return "errorPage"; // Thymeleaf template name for error handling
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid user ID format: " );
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve auction: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }


    @GetMapping("/closed/getAuction")
    public String getAuction(@RequestParam(value = "auctionId") Long auctionId,Model model) {
        try {

            AuctionDTO auction = auctionService.getAuctionById(auctionId);
            if (auction != null) {
                model.addAttribute("auction", auction);
                return "auctionDetails"; // Thymeleaf template name
            } else {
                model.addAttribute("error", "Auction not found with ID " + auctionId);
                return "errorPage"; // Thymeleaf template name for error handling
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid user ID format: " );
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve auction: " + e.getMessage());
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
