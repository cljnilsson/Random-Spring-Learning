package com.Lukas.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Using component enabled autowired but might be correct here
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler
{
    //private final RedirectStrategy redirectStrategy;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        String name = authentication.getName();
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;
        String provider = token.getAuthorizedClientRegistrationId();
        CryptoAuthenticatedPrincipal principal;
        switch (provider) {
            case "google":
                principal = (CryptoAuthenticatedPrincipal)authentication.getPrincipal();
                System.out.println(principal.getName());
                System.out.println(principal.getImg());
                break;
            case "github":
                principal = (CryptoAuthenticatedPrincipal)authentication.getPrincipal();
                System.out.println(principal.getName());
                System.out.println(principal.getImg());
                break;
        }
        // Register user here?? or remap principal variables somehow
        //System.out.println(provider);
        //System.out.println(name);
        //System.out.println(authentication.getAuthorities());
        response.sendRedirect("/");
    }
}
