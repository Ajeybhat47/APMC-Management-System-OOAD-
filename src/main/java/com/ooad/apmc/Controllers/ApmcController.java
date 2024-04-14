package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ooad.apmc.DTOModels.AuctionDTO;
import com.ooad.apmc.Models.Auction;
import com.ooad.apmc.Service.APMCService;
import com.ooad.apmc.Service.AuctionService;

import java.util.List;

@Controller
@RequestMapping("/apmc")
public class ApmcController {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private APMCService apmcService;

    @GetMapping("/{userType}/{userId}")
    public String landingPage(@PathVariable String userType, @PathVariable Long userId, Model model) {
        
        model.addAttribute("userId", userId);
        model.addAttribute("userType", userType); // Add userType as an attribute

        if(userType.equals("trader"))
            return "trader/traderDashboard"; // Thymeleaf template name
        else if(userType.equals("farmer"))
            return "farmer/farmerDashboard"; // Thymeleaf template name
        else if(userType.equals("worker"))
            return "worker/workerDashboard"; // Thymeleaf template name
        else
            return "errorPage"; // Thymeleaf template name for error handling
    }


    @GetMapping("/auction/createAuction")
    public String getMethodName() {
        return "admin/createAuction"; // Thymeleaf template name
    }

    @PostMapping("/auction/createAuction")
    public String createAuction(@ModelAttribute("auction") Auction auction, @RequestParam("itemId") Long itemId, Model model) {
        try {
            String result = auctionService.createAuction(auction, itemId);
            model.addAttribute("result", result);

            return "admin/auctionCreated"; // Thymeleaf template name

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create auction: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }

    @GetMapping("/auction/getAllAuctions")
    public String getAllAuctions(Model model) {
        try {
            List<AuctionDTO> auctions = apmcService.getAuctions();
            model.addAttribute("auctions", auctions);
            return "allAuctions"; // Thymeleaf template name
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve auctions: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }

    @GetMapping("/auction/getAllActiveAuctions")
    public String getAllActiveAuctions(@RequestParam(value = "userType")String userType,@RequestParam(value = "userId") String userId,Model model) {
        try {
            List<AuctionDTO> auctions = auctionService.getAllAuctionsByStatus("active");
            model.addAttribute("auctions", auctions);
            model.addAttribute("userId", userId);
            model.addAttribute("userType", userType);
            return userType+"/activeAuctions"; // Thymeleaf template name
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve active auctions: " + e.getMessage());
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


}
