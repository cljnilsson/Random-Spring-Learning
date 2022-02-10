package com.Lukas.demo.model;

import com.Lukas.demo.service.CryptoAuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GithubUser extends DefaultOAuth2User implements CryptoAuthenticatedPrincipal
{
    public GithubUser(OAuth2User user) {
        super(user.getAuthorities(), user.getAttributes(), "name");
    }

    @Override
    public String getName() {
        return this.getAttribute("name");
    }

    @Override
    public String getImg()
    {
        return this.getAttribute("avatar_url");
    }

    @Override
    public String getId() {
        return this.getAttribute("id").toString();
    }
}
