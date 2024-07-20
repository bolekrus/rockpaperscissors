package com.example.rockpaperscissors.service.impl;

import com.example.rockpaperscissors.dto.UserDTO;
import com.example.rockpaperscissors.entity.User;
import com.example.rockpaperscissors.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getCurrentUser() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getCurrentUser();
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());

        verify(securityContext).getAuthentication();
        verify(authentication).getName();
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void isUsernameExists() {
        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(new User()));
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());

        assertTrue(userService.isUsernameExists("existinguser"));
        assertFalse(userService.isUsernameExists("newuser"));

        verify(userRepository).findByUsername("existinguser");
        verify(userRepository).findByUsername("newuser");
    }

    @Test
    void registerUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("newuser");
        userDTO.setPassword("password");

        User user = new User();
        user.setUsername("newuser");
        user.setPasswordHash("hashedpassword");

        when(passwordEncoder.encode("password")).thenReturn("hashedpassword");

        userService.registerUser(userDTO);

        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
    }
}