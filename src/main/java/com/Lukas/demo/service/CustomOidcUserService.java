package com.Lukas.demo.service;

import com.Lukas.demo.model.GoogleUser;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class CustomOidcUserService extends OidcUserService
{
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException
    {
        GoogleUser user = new GoogleUser(super.loadUser(userRequest));
        return user;
    }
}
