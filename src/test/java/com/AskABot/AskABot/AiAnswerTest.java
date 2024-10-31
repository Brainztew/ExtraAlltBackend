package com.AskABot.AskABot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.AskABot.AskABot.model.ChatRequest;
import com.AskABot.AskABot.model.ChatResponse;
import com.AskABot.AskABot.model.Message;
import com.AskABot.AskABot.model.Topic;
import com.AskABot.AskABot.repository.TopicRepository;
import com.AskABot.AskABot.service.TopicService;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

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
        topicService.apiUrl = "http://mock-api-url";
    }

@Test
public void testAddAiMessageToTopic2_Success() {
    String prompt = "Test Prompt";
    String topicId = "topicId";

    Topic topic = new Topic();
    topic.setTopicName("Test Topic");
    topic.setTopicId("topicId");
    topic.setCreatedByUser("123");

    Message message = new Message("assistant", "Test response");
    ChatResponse.Choice choice = new ChatResponse.Choice();
    choice.setMessage(message);
    ChatResponse chatResponse = new ChatResponse();
    chatResponse.setChoices(Collections.singletonList(choice));

    when(restTemplate.postForObject(anyString(), any(ChatRequest.class), any())).thenReturn(chatResponse);
    when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

    String response = topicService.addAiMessageToTopic2(topicId, prompt);

    assertEquals("Test response", response);
}

        @Test
    public void testAddAiMessageToTopic2_TopicNotFound() {
        String prompt = "Test Prompt";
        String topicId = "topicId";

        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

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