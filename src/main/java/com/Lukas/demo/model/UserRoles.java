package com.Lukas.demo.model;

import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
public class UserRoles
{
    @Id
    @GeneratedValue
    private Long id;

    public Long getId()
    {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "role")
    private AllRoles role;

    public String getRole()
    {
        return "ROLE_" + role.getName().toUpperCase();
    }

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public User getUser()
    {
        return user;
    }
}
