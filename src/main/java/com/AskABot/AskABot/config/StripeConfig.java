package com.AskABot.AskABot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@Configuration
public class StripeConfig {
    
    @Value("${stripeKey}")
    private String stripeKey;

     @PostConstruct
    public void init() {
        Stripe.apiKey = stripeKey;
    }
    
}
