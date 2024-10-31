package com.AskABot.AskABot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.AskABot.AskABot.model.ChatRequest;
import com.AskABot.AskABot.model.ChatResponse;
import com.AskABot.AskABot.model.Topic;

@Service
public class AiAnswerService {
    
    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public AiAnswerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAiAnswer(String prompt, Topic topic) {
        String aiPersonality = topic.getTopicName();
        String modifiedPrompt = "Respond in the style of " + aiPersonality + " make it short and fun, never go out of Character" + ": " + prompt;
        ChatRequest chatRequest = new ChatRequest("gpt-4o", modifiedPrompt, 1);
        ChatResponse chatResponse = restTemplate.postForObject(apiUrl, chatRequest, ChatResponse.class);
        if (chatResponse == null || chatResponse.getChoices() == null || chatResponse.getChoices().isEmpty() || chatResponse.getChoices().get(0).getMessage() == null) {
            throw new RuntimeException("Invalid response from AI service");
        }
        String message = chatResponse.getChoices().get(0).getMessage().getContent();
        return message;   
    }

    
    
}
