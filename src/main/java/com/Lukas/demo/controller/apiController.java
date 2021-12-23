package com.Lukas.demo.controller;

import com.Lukas.demo.model.ToDoItem;
import com.Lukas.demo.repository.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class apiController
{
    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<ToDoItem> allToDos() {
        return toDoItemRepository.findAll();
    }

    @PostMapping("/add")
    public @ResponseBody String addToDo(@RequestParam String name, @RequestParam Boolean done) {
        ToDoItem n = new ToDoItem();
        n.setName(name);
        n.setDone(done);
        toDoItemRepository.save(n);
        return "Saved";
    }

    @PostMapping("/update")
    public @ResponseBody String updateToDo(@RequestParam Long id, @RequestParam String name, @RequestParam Boolean done) {
        ToDoItem n = toDoItemRepository.findById(id).get(); // Lazily assumes its never null.
        n.setName(name);
        n.setDone(done);
        toDoItemRepository.save(n);
        return "Saved";
    }


    @GetMapping("/template")
    public ModelAndView template() {
        Map<String, Object> data = new HashMap<>();
        List<ToDoItem> all = toDoItemRepository.findAll();
        data.put("all", all);
        return new ModelAndView("baba", data);
    }
}