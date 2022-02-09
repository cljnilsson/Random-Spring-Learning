package com.Lukas.demo;

import com.Lukas.demo.model.User;
import com.Lukas.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserRepository users;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        String name = authentication.getName();
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;
        String provider = token.getAuthorizedClientRegistrationId();
        CryptoAuthenticatedPrincipal principal;

        principal = (CryptoAuthenticatedPrincipal)authentication.getPrincipal();
        System.out.println(principal.getName());
        System.out.println(principal.getImg());

        switch (provider) {
            case "google":
                break;
            case "github":
                break;
        }

        User u = users.findOneByOauthIdAndProvider(provider, principal.getId());
        Boolean exists = u == null ? false : true;
        System.out.println(exists);

        if(!exists) {
            u = new User();
            u.setOauthId(principal.getId());
            u.setOauthProvider(provider);
            users.save(u);
            System.out.println("User created with id: " + principal.getId() + " for provider: " + provider);
        }

        //System.out.println(provider);
        //System.out.println(name);
        //System.out.println(authentication.getAuthorities());
        response.sendRedirect("/");
    }
}
