package com.Lukas.demo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AllRoles
{
    @Id
    @GeneratedValue
    private Long id;
    public Long getId() {
        return id;
    }

    private String name;

    @OneToMany
    private Set<UserRoles> users;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Set<UserRoles> getUsers()
    {
        return users;
    }

    public void setUsers(Set<UserRoles> users)
    {
        this.users = users;
    }
}
