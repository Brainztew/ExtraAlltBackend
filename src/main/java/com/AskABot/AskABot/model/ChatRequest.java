package com.AskABot.AskABot.model;

import java.util.ArrayList;
import java.util.List;

public class ChatRequest {

    private String model;
    private List<Message> messages;
    private int n;

    public ChatRequest(String model, String prompt, int n) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", "Hello! I am a bot. How can I help you today?"));
        this.messages.add(new Message("user", prompt));
        this.n = n;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    
}