package com.AskABot.AskABot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.AskABot.AskABot.model.User;
import com.AskABot.AskABot.repository.UserRepository;
import com.AskABot.AskABot.service.UserService;

public class UserTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testRegisterUserAndPassword() {
        User user = new User();

        user.setEmail("isac@test.se");
        user.setPassword("testPassword");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
        when(passwordEncoder.matches("testPassword", "encodedPassword")).thenReturn(true);
        
        User savedUser = new User();

        savedUser.setEmail("isac@test.se");
        savedUser.setPassword("encodedPassword");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("encodedPassword", registeredUser.getPassword());

        assert(passwordEncoder.matches("testPassword", registeredUser.getPassword()));

    }

    @Test
    public void testLoginUserWithRightPassword() {
        User user = new User();
        user.setEmail("isac@test.se");
        user.setPassword(passwordEncoder.encode("testPassword"));

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(passwordEncoder.matches("testPassword", user.getPassword())).thenReturn(true);
        
        User loginUser = new User();
        loginUser.setEmail("isac@test.se");
        loginUser.setPassword("testPassword");

        User loggedInUser = userService.loginUser(loginUser);

        assertEquals(user.getEmail(), loggedInUser.getEmail());
        assertEquals(user.getPassword(), loggedInUser.getPassword());
    }

        @Test
    void testEquals() {
        User user1 = new User();
        user1.setUserId("123");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setUserId("123");
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");

        assertEquals(user1, user2);
    }

    @Test
    void testNotEquals() {
        User user1 = new User();
        user1.setUserId("123");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setUserId("456");
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");

        assertNotEquals(user1, user2);
    }

    @Test
    void testHashCode() {
        User user1 = new User();
        user1.setUserId("123");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setUserId("123");
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testHashCodeNotEquals() {
        User user1 = new User();
        user1.setUserId("123");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setUserId("456");
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");

        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

}
