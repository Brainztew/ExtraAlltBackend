package com.AskABot.AskABot.model;

import java.util.List;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "topics")
public class Topic {
    @Id
    private String topicId;
    private String topicName;
    private String createdByUser;
    private Boolean aiActive;
    private List<String> messages = new ArrayList<>();
    private List<User> usersInTopic;

    public Topic() {
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Boolean getAiActive() {
        return aiActive;
    }

    public void setAiActive(Boolean aiActive) {
        this.aiActive = aiActive;
    }

    public List<User> getUsersInTopic() {
        return usersInTopic;
    }

    public void setUsersInTopic(List<User> usersInTopic) {
        this.usersInTopic = usersInTopic;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    
    
}