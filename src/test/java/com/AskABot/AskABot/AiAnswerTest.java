package com.AskABot.AskABot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.AskABot.AskABot.model.ChatRequest;
import com.AskABot.AskABot.model.ChatResponse;
import com.AskABot.AskABot.model.ChatResponse.Choice;
import com.AskABot.AskABot.model.Message;
import com.AskABot.AskABot.model.Topic;
import com.AskABot.AskABot.repository.TopicRepository;
import com.AskABot.AskABot.service.TopicService;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;

public class AiAnswerTest {

    @Value("${openai.api.url}")
    private String apiUrl;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TopicService topicService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

/* @Test
public void testAddAiMessageToTopic2_Success() {
    String prompt = "Test Prompt";
    String topicId = "topicId";

    Topic topic = new Topic();
    topic.setTopicName("Test Topic");
    topic.setTopicId("topicId");
    topic.setCreatedByUser("123");

    ChatResponse chatResponse = new ChatResponse();
    chatResponse.setChoices(new ArrayList<Choice>());

    ChatRequest chatRequest = new ChatRequest("gpt-4o", prompt, 1);
    
    when(restTemplate.postForObject(anyString(), any(ChatRequest.class), any())).thenReturn(chatResponse);
    when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
  
    // Act
    String response = topicService.addAiMessageToTopic2(topicId, prompt);

    // Assert
    assertEquals("Test response", response);
} */

        @Test
    public void testAddAiMessageToTopic2_TopicNotFound() {
        String prompt = "Test Prompt";
        String topicId = "topicId";

        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            topicService.addAiMessageToTopic2(prompt, topicId);
        });
    }

    @Test
    public void testAddAiMessageToTopic2_InvalidResponse() {
        String prompt = "Test Prompt";
        String topicId = "topicId";

        Topic topic = new Topic();
        topic.setTopicName("Test Topic");
        topic.setTopicId(topicId);
        topic.setCreatedByUser("123");

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(restTemplate.postForObject(anyString(), any(ChatRequest.class), any())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            topicService.addAiMessageToTopic2(prompt, topicId);
        });
    }
}