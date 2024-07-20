package com.example.rockpaperscissors.service;

import com.example.rockpaperscissors.dto.UserDTO;
import com.example.rockpaperscissors.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getCurrentUser();

    boolean isUsernameExists(String username);

    public void registerUser(UserDTO userDTO);
}
