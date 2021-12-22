package com.Lukas.demo.service;

import com.Lukas.demo.model.ToDoItem;
import com.Lukas.demo.repository.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ToDoItemService
{
    @Autowired
    private ToDoItemRepository toDoItemRepositoryRepository;

    public List<ToDoItem> list() {
        return toDoItemRepositoryRepository.findAll();
    }
}
