package com.Lukas.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ToDoItem
{
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Boolean done;
}
