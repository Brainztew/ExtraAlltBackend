package com.AskABot.AskABot;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.AskABot.AskABot.model.Topic;
import com.AskABot.AskABot.model.User;
import com.AskABot.AskABot.repository.TopicRepository;
import com.AskABot.AskABot.service.TopicService;

public class TopicTest {

    @Mock 
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateThread() {
        User user = new User();
        user.setEmail("isac@test.se");
        user.setPassword("testPassword");
        user.setUserId("123");

        Topic topic = new Topic();
        topic.setTopicName("Test Topic");
        topic.setCreatedByUser("123");

       when(topicRepository.save(any(Topic.class))).thenReturn(topic);
    }
    
}
