package com.Lukas.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class User
{
    @Id
    @GeneratedValue
    private Long id;

    private String oauthId;
    private String oauthProvider;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<UserRoles> roles;
}
