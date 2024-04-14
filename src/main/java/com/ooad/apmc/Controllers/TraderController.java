package com.ooad.apmc.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ooad.apmc.DTOModels.AuctionDTO;
import com.ooad.apmc.DTOModels.BidDTO;
import com.ooad.apmc.Service.TraderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/apmc/trader/")
public class TraderController {

    @Autowired
    private TraderService traderService;

    @GetMapping("/History")
    public String getHistory(@RequestParam(value = "traderId") Long userId, Model model) {
        try {
            List<BidDTO> bids = traderService.getHistory(userId);
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

    @GetMapping("/WonAuctions")
    public String getWonAuctions(@RequestParam(value = "traderId") Long userId, Model model) {
        try {
            List<AuctionDTO> auctions = traderService.getWonAuctions(userId);
            model.addAttribute("Auctions", auctions);
            return "trader/winList"; // Thymeleaf template name
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve bids: " + e.getMessage());
            return "errorPage"; // Thymeleaf template name for error handling
        }
    }
}
