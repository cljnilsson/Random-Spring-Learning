package com.Lukas.demo.controller;

import com.Lukas.demo.model.ToDoItem;
import com.Lukas.demo.repository.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class routeController
{
    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @GetMapping("/")
    public ModelAndView template() {
        Map<String, Object> data = new HashMap<>();
        List<ToDoItem> all = toDoItemRepository.findAll();
        data.put("all", all);
        return new ModelAndView("todo-crud", data);
    }
}
