package com.Lukas.demo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
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

    public void setRole(AllRoles role)
    {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

}
