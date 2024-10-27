package com.AskABot.AskABot;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


import com.AskABot.AskABot.repository.TopicRepository;
import com.AskABot.AskABot.service.AiAnswerService;
import com.AskABot.AskABot.service.TopicService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

public class AiAnswerTest {
    @Mock
    private TopicRepository topicRepository;

    @Mock
    private AiAnswerService aiAnswerService;

    @InjectMocks
    private TopicService topicService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAiMessageToTopic_TopicNotFound() {
        // Arrange
        String topicId = "1";
        String content = "Test content";
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            topicService.addAiMessageToTopic(topicId, content);
        });

        verify(topicRepository, times(1)).findById(topicId);
        verifyNoMoreInteractions(topicRepository);
        verifyNoInteractions(aiAnswerService);
    }
}