package com.AskABot.AskABot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.AskABot.AskABot.model.Item;
import com.AskABot.AskABot.service.ItemService;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@CrossOrigin(origins = {"http://localhost:5173", "https://coral-app-ei5fb.ondigitalocean.app"})
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/createItem")
    public Item createItem(@RequestBody Item item) {
        return itemService.createItem(item);   
    }

    @GetExchange("/getItems")
    public List<Item> getItems() {
        return itemService.getAllItems();
    }
}
