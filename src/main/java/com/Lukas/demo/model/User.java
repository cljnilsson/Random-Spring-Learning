package com.Lukas.demo.model;

import com.Lukas.demo.repository.AllRolesRepository;
import com.Lukas.demo.repository.UserRepository;
import com.Lukas.demo.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class User
{
    @Id
    @GeneratedValue
    private Long id;
    public Long getId() {
        return id;
    }

    private String oauthId;
    private String oauthProvider;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<UserRoles> roles;

    @Column
    public String getOauthProvider()
    {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider)
    {
        this.oauthProvider = oauthProvider;
    }

    public String getOauthId()
    {
        return oauthId;
    }

    public void setOauthId(String oauthId)
    {
        this.oauthId = oauthId;
    }

    public ArrayList<UserRoles> getRoles()
    {
        return new ArrayList<>(this.roles);
    }

    public void setRoles(Set<UserRoles> roles)
    {
        this.roles = roles;
    }
}
