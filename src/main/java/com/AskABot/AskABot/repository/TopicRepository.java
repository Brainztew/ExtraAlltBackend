package com.AskABot.AskABot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.AskABot.AskABot.model.Topic;

public interface TopicRepository extends MongoRepository<Topic, String> {
    Topic findByTopicId(String topicId);
}
