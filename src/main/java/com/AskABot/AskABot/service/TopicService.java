package com.AskABot.AskABot.service;

import org.springframework.stereotype.Service;

import com.AskABot.AskABot.model.Topic;
import com.AskABot.AskABot.repository.TopicRepository;

@Service
public class TopicService {

    private TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic createTopic(Topic topic) {
        if (topic.getTopicName() == null) {
            throw new IllegalArgumentException("Topic name cannot be null");
        } if (topic.getCreatedByUser() == null) {
            throw new IllegalArgumentException("Must have a creator");
        }else {
            return topicRepository.save(topic);
        }
        
    }

    public Iterable<Topic> getTopics() {
        return topicRepository.findAll();
    }

    public void deleteTopic(String topicId, String userId) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            throw new IllegalArgumentException("Topic not found");
        } 
        if (topic.getCreatedByUser().equals(userId)) {
            topicRepository.delete(topic);
        } else {
            throw new IllegalArgumentException("User does not have permission to delete this topic");
        }
        
    }
    
}
