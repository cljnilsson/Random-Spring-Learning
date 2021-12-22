package com.Lukas.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ToDoItem
{

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    public String getName()
    {
        return name;
    }
    public void setName(String s) {
        name = s;
    }

    private Boolean done;
    public Boolean getDone() {
        return done;
    }
    public void setDone(Boolean b) {
        done = b;
    }

    // standard constructors

    // standard getters and setters
}
