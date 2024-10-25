package com.AskABot.AskABot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

@Configuration
@CrossOrigin(origins = {"http://localhost:5173", "https://coral-app-ei5fb.ondigitalocean.app"})
public class OpenAiRestTemplate {
    
    @Value("${openai.api.key}")
    private String apiKey;

    @Bean
    @Qualifier("openAiRestTemplate")
    public RestTemplate openaiRestTemplate() {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + apiKey);
            return  execution.execute(request, body);
        });
        return restTemplate;
    }
}