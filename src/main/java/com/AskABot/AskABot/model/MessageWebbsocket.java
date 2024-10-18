package com.AskABot.AskABot.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class MessageWebbsocket {
    private String sender;
    private String content;

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

    
}
