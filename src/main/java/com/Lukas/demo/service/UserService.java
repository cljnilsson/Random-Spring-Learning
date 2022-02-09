package com.Lukas.demo.service;

import com.Lukas.demo.model.ToDoItem;
import com.Lukas.demo.model.User;
import com.Lukas.demo.repository.ToDoItemRepository;
import com.Lukas.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public List<User> list() {
        return userRepository.findAll();
    }
}
