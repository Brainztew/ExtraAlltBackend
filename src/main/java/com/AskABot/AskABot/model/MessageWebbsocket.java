package com.AskABot.AskABot.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messagesWebsocket")
public class MessageWebbsocket {
    private String sender;
    private String content;
    private String topicId;

    public MessageWebbsocket() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
    
    
}
