package com.AskABot.AskABot.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.AskABot.AskABot.model.User;
import com.AskABot.AskABot.repository.UserRepository;

@Service
public class UserService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public User registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        } 
         if (user.getPassword().length() < 6) {
           throw new IllegalArgumentException("Password must be at least 6 characters long");
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            return userRepository.save(user);
        }    
    }

    public User loginUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) {
            throw new IllegalArgumentException("User does not exist");
        } else if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        } else {
            System.out.println("User logged in: " + existingUser.getEmail());
            return existingUser;
        }
    }

        public String getUserIdByEmail(String email) {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new IllegalArgumentException("User does not exist");
            } else {
                return user.getUserId();
            }
        }
}

