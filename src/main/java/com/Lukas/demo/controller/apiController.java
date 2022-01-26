package com.Lukas.demo.controller;

import com.Lukas.demo.model.ToDoItem;
import com.Lukas.demo.repository.ToDoItemRepository;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
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
    public ResponseEntity updateToDo(@RequestParam @Min(0) Long id, @RequestParam @Max(20) String name, @RequestParam Boolean done) {
        ToDoItem n = toDoItemRepository.findById(id).orElse(null);
        if(n != null) { //backup validation, @min and @max should cover it though.
            n.setName(name);
            n.setDone(done);
            toDoItemRepository.save(n);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity deleteToDo(@RequestParam @Min(0) Long id) {
        toDoItemRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/template")
    public ModelAndView template() {
        Map<String, Object> data = new HashMap<>();
        List<ToDoItem> all = toDoItemRepository.findAll();
        data.put("all", all);
        return new ModelAndView("todo-crud", data);
    }
}