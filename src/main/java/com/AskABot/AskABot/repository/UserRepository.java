package com.AskABot.AskABot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.AskABot.AskABot.model.User;

public interface UserRepository extends MongoRepository<User, String>{

    User findByEmail(String email);

    
}
