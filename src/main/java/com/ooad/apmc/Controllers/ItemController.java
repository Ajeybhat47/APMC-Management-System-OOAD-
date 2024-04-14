package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ooad.apmc.Models.Item;
import com.ooad.apmc.Service.ItemService;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/create")
    public String showCreateItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "item/createItemForm"; // Assuming you have a Thymeleaf template named "createItemForm.html"
    }

    @PostMapping("/create")
    public String createItem(@ModelAttribute Item item, @RequestParam("farmerId") Long farmerId, Model model) {
        try {
            itemService.createItem(item, farmerId);
            return "redirect:/item/" + item.getItemId(); // Redirect to view item details
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
