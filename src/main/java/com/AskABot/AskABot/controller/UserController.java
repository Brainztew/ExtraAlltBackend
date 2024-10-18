package com.AskABot.AskABot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskABot.AskABot.model.User;
import com.AskABot.AskABot.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequestMapping("/user")
@RestController
public class UserController {

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    @Autowired  
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email cannot be null");
        } else if (user.getPassword() == null || user.getPassword().length() < 6) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 6 characters long");
        }
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
    
    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            String userId = userService.getUserIdByEmail(user.getEmail());
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            User dbUser = userService.loginUser(user);
            if (dbUser != null) {
                Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
                String token = Jwts.builder()
                        .setSubject(userId.toString())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                        .signWith(key, SignatureAlgorithm.HS512)
                        .compact();
                    Map<String, String> response = new HashMap<>();
                        response.put("token", token);
                        response.put("userId", userId);
                return ResponseEntity.ok(response);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    }
}
    

