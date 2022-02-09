package com.Lukas.demo;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

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
}
