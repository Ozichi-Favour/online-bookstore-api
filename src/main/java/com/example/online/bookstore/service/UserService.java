package com.example.online.bookstore.service;


import com.example.online.bookstore.dto.UserDTO;
import com.example.online.bookstore.model.User;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}