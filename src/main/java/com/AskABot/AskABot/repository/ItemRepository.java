package com.AskABot.AskABot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.AskABot.AskABot.model.Item;

public interface ItemRepository extends MongoRepository<Item, String> {

    Item findByItemId(String itemId);
    
}
