package com.Lukas.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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


    @Column
    public String getOauthProvider()
    {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider)
    {
        this.oauthProvider = oauthProvider;
    }

    @Column
    public String getOauthId()
    {
        return oauthId;
    }

    public void setOauthId(String oauthId)
    {
        this.oauthId = oauthId;
    }
}
