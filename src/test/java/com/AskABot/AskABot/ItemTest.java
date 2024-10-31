package com.AskABot.AskABot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.AskABot.AskABot.model.Item;
import com.AskABot.AskABot.repository.ItemRepository;
import com.AskABot.AskABot.service.ItemService;

public class ItemTest {

    @Mock 
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateItem() {
        Item item = new Item();
        item.setTitle("Test Item");
        item.setPrice(100);
        item.setStock(1);
        item.setItemId("1");

        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item savedItem = itemService.createItem(item);

        assertEquals(item, savedItem);
    }
    
}
