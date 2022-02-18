package com.Lukas.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class AllRoles
{
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    @OneToMany
    private Set<UserRoles> users;
}
