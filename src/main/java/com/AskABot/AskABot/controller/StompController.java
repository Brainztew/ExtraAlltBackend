package com.AskABot.AskABot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskABot.AskABot.model.MessageWebbsocket;

import com.AskABot.AskABot.service.TopicService;

@RestController
@RequestMapping("/api/Topic")
@CrossOrigin(origins = "http://localhost:5173")
public class StompController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/welcome/{topicId}")
    public void sendWelcomeMessage(@DestinationVariable String topicId, MessageWebbsocket message) {
        System.out.println("WELCOME");
        messagingTemplate.convertAndSend("/topic/welcome/" + topicId, message);
    }

    @MessageMapping("/{topicId}")
    @SendTo("/topic/{topicId}")
    public MessageWebbsocket sendMessage(@DestinationVariable String topicId, MessageWebbsocket message) {
        topicService.addMessageToTopic(message);
        return message;
    }

}
