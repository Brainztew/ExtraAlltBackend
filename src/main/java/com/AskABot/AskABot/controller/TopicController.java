package com.AskABot.AskABot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.AskABot.AskABot.model.Topic;
import com.AskABot.AskABot.service.TopicService;

@RestController
@RequestMapping("/topic")
@CrossOrigin(origins = "*")
public class TopicController {

    @Autowired
    TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/createTopic")
    public Topic createTopic(@RequestBody Topic topic) {
        return topicService.createTopic(topic);
        
    }

    @GetMapping("/getTopics")
    public Iterable<Topic> getTopics() {
        return topicService.getTopics();
    }

    @DeleteMapping("/deleteTopic")
    public void deleteTopic(@RequestParam String topicId, @RequestParam String userId) {
        topicService.deleteTopic(topicId, userId);
    }
    
}
