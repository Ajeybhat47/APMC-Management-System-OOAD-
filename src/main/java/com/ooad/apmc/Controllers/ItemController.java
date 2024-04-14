package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ooad.apmc.Models.Item;
import com.ooad.apmc.Service.ItemService;
import com.ooad.apmc.Models.Auction;
import com.ooad.apmc.Service.*;
import com.ooad.apmc.Service.AuctionFacade;
import com.ooad.apmc.Models.Bid;
import java.util.NoSuchElementException;
import java.util.List;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private AuctionService auctionService;
    @Autowired
private AuctionFacade auctionFacade;
    // @GetMapping("/create")
    // public String showCreateItemForm(Model model) {
    //     model.addAttribute("item", new Item());
    //     return "item/createItemForm"; // Assuming you have a Thymeleaf template named "createItemForm.html"
    // }
    @Autowired
    private BidService bidService;

    @GetMapping("/create/{userId}")
    public String showCreateItemForm(@PathVariable Long userId, Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("userId", userId);
        return "item/createItemForm"; // Assuming you have a Thymeleaf template named "createItemForm.html"
    }


    // @PostMapping("/create/{userId}")
    // public String createItem(@ModelAttribute Item item, @RequestParam("farmerId") Long farmerId, Model model) {
    //     try {
    //         itemService.createItem(item, farmerId);
    //         return "redirect:/item/" + item.getItemId(); // Redirect to view item details
    //     } catch (IllegalArgumentException | NoSuchElementException e) {
    //         model.addAttribute("error", e.getMessage());
    //         return "errorPage"; // Assuming you have an error page template
    //     } catch (RuntimeException e) {
    //         model.addAttribute("error", "Error creating item: " + e.getMessage());
    //         return "errorPage"; // Assuming you have an error page template
    //     }
    // }
@GetMapping("/item/{userId}")
public String getItemsByUser(@PathVariable("userId") Long userId, Model model) {
    try {
        List<Item> items = itemService.getItemsByUser(userId);

        // Filter out items where item or item.seller is null
        List<Item> validItems = items.stream()
            .filter(item -> item != null && item.getSeller() != null)
            .collect(Collectors.toList());

        // Retrieve the highest bid for each item
        Map<Long, Bid> highestBids = new HashMap<>();
        for (Item item : validItems) {
            Bid highestBid = bidService.getHighestBidForItem(item.getItemId());
            highestBids.put(item.getItemId(), highestBid);
        }

        model.addAttribute("items", validItems);
        model.addAttribute("highestBids", highestBids);

        return "item/itemsList"; // Assuming you have a itemsList template
    } catch (IllegalArgumentException | NoSuchElementException e) {
        model.addAttribute("error", e.getMessage());
        return "errorPage"; // Assuming you have an error page template
    } catch (RuntimeException e) {
        model.addAttribute("error", "Error retrieving items: " + e.getMessage());
        return "errorPage"; // Assuming you have an error page template
    }
}

    // @PostMapping("/item/{userId}")
    // public String endAuction(@PathVariable("userId") Long userId, @RequestParam("itemId") Long itemId, Model model) {
    //     try {
    //         String result = auctionFacade.closeAuction(itemId);
    //         model.addAttribute("result", result);
    //     } catch (NoSuchElementException e) {
    //         model.addAttribute("message", "No bids found for this auction");
    //         return "item/itemsList"; // Thymeleaf template name for displaying the auction result
    //     }
    //     return "item/itemsList"; // Thymeleaf template name for displaying the auction result
    // }

@PostMapping("/create/{userId}")
public String createItem(@ModelAttribute Item item, @PathVariable("userId") Long farmerId, Model model) {
    try {
        Item savedItem = itemService.createItem(item, farmerId);

        // Create a new auction
        Auction auction = new Auction();
        auction.setItem(savedItem);
        auction.setBasePrice(savedItem.getInitialPrice()); // Assuming the base price is the same as the item price
        auction.setClosingTime(LocalDateTime.now().plusDays(7)); // Assuming the auction closes in 7 days
        auction.setStatus("OPEN"); // Assuming the auction status is "OPEN" when created
        // The winner_id and winning_bid can be set when a bid is made

        // Save the auction
        auctionService.saveAuction(auction);

        return "redirect:/item/item/" + farmerId; // Redirect to view item details
    } catch (IllegalArgumentException | NoSuchElementException e) {
        model.addAttribute("error", e.getMessage());
        return "errorPage"; // Assuming you have an error page template
    } catch (RuntimeException e) {
        model.addAttribute("error", "Error creating item: " + e.getMessage());
        return "errorPage"; // Assuming you have an error page template
    }
}


    @GetMapping("/{itemId}")
    public String getItemById(@PathVariable("itemId") Long itemId, Model model) {
        try {
            Item item = itemService.getItemById(itemId);
            model.addAttribute("item", item);
            return "itemDetails"; // Assuming you have a Thymeleaf template named "itemDetails.html"
        } catch (NoSuchElementException e) {
            model.addAttribute("error", "Item not found");
            return "errorPage"; // Assuming you have an error page template
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error fetching item");
            return "errorPage"; // Assuming you have an error page template
        }
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestParam("itemId") Long itemId) {
        try {
            itemService.deleteItem(itemId);
            return "redirect:/"; // Redirect to home page or any other appropriate page
        } catch (RuntimeException e) {
            // Handle error if needed
            return "redirect:/"; // Redirect to home page or any other appropriate page
        }
    }
}
