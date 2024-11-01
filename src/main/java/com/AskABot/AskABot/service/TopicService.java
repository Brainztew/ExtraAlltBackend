package com.AskABot.AskABot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Iterator;

import com.AskABot.AskABot.model.ChatRequest;
import com.AskABot.AskABot.model.ChatResponse;
import com.AskABot.AskABot.model.MessageWebbsocket;
import com.AskABot.AskABot.model.Topic;
import com.AskABot.AskABot.model.User;
import com.AskABot.AskABot.repository.TopicRepository;
import com.AskABot.AskABot.repository.UserRepository;

@Service
public class TopicService {

   @Value("${openai.api.url}")
    public String apiUrl;

    private TopicRepository topicRepository;
    private UserRepository userRepository;
    private final RestTemplate restTemplate;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository, RestTemplate restTemplate) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public Topic createTopic(Topic topic) {
        if (topic.getTopicName() == null) {
            throw new IllegalArgumentException("Topic name cannot be null");
        } 
        if (topic.getCreatedByUser() == null) {
            throw new IllegalArgumentException("Must have a creator");
        }else {
            topic.setUsersInTopic(Collections.emptyList());
            return topicRepository.save(topic);
        }
        
    }

    public Iterable<Topic> getTopics() {
        return topicRepository.findAll();
    }

    public void deleteTopic(String topicId, String userId) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            throw new IllegalArgumentException("Topic not found");
        } 
        if (topic.getCreatedByUser().equals(userId)) {
            topicRepository.delete(topic);
        } else {
            throw new IllegalArgumentException("User does not have permission to delete this topic");
        }
        
    }

    public void joinTopic(String userId, String topicId) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            throw new IllegalArgumentException("Topic not found");
        } 
        User user = userRepository.findById(userId).orElse(null);
        if (topic.getUsersInTopic().contains(user)) {
            System.out.println("User already in topic: " + user.getEmail());
            return;
        }
        if (user == null) {
            System.out.println("null");
            return;
        }
        topic.getUsersInTopic().add(user);
        topicRepository.save(topic);
    }

    public void leaveTopic(String userId, String topicId) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            throw new IllegalArgumentException("Topic not found");
        } 
        User user = userRepository.findById(userId).orElse(null);
        topic.getUsersInTopic().remove(user);
        System.out.println("User left topic: " + user.getEmail());
        
        topicRepository.save(topic);
    }

    public void addMessageToTopic(MessageWebbsocket message) {
        Topic topic = topicRepository.findById(message.getTopicId()).orElse(null);
        if (topic == null) {
            throw new IllegalArgumentException("Topic not found");
        }
        String messageString = message.getSender() + ": " + message.getContent();
        topic.getMessages().add(messageString);
        topicRepository.save(topic);
    }

    public Topic getTopicById(String topicId) {
        return topicRepository.findById(topicId).orElse(null);
    }

    public Iterable<String> getMessagesInTopic(String topicId) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            throw new IllegalArgumentException("Topic not found");
        }
        return topic.getMessages();
    }

    public Iterable<String> getUsersInTopic(String topicId) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            throw new IllegalArgumentException("Topic not found");
        }
        Iterable<User> users = topic.getUsersInTopic();
        Iterable<String> emails = new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    private Iterator<User> userIterator = users.iterator();
                    @Override
                    public boolean hasNext() {
                        return userIterator.hasNext();
                    }

                    @Override
                    public String next() {
                        return userIterator.next().getEmail();
                    }
                };
            }
        };
        return emails;
    }

/*     public void addAiMessageToTopic(String topicId, String content) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            throw new IllegalArgumentException("Topic not found");
        }
        String answer = AiAnswerService.getAiAnswer(content, topic);
        topic.getMessages().add("AI bot: " + answer);
        topicRepository.save(topic);
    } */

    public String addAiMessageToTopic2(String topicId, String prompt ) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            throw new IllegalArgumentException("Topic not found");
        }
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
