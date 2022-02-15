package com.Lukas.demo.service;

import com.Lukas.demo.model.GithubUser;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOauth2UserService extends DefaultOAuth2UserService
{

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException
    {
        GithubUser user = new GithubUser(super.loadUser(userRequest));
        return user;
    }
}
