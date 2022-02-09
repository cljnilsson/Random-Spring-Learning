package com.Lukas.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class ToDoItem
{
    @Id
    @GeneratedValue
    private Long id;
    public Long getId() {
        return id;
    }

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
