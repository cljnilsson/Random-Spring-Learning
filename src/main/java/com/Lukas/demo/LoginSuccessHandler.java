package com.Lukas.demo;

import com.Lukas.demo.model.AllRoles;
import com.Lukas.demo.model.User;
import com.Lukas.demo.model.UserRoles;
import com.Lukas.demo.repository.AllRolesRepository;
import com.Lukas.demo.repository.UserRepository;
import com.Lukas.demo.repository.UserRolesRepository;
import com.Lukas.demo.service.CustomAuthenticatedPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Using component enabled autowired but might be correct here
@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler
{
    @Autowired
    UserRepository users;

    @Autowired
    UserRolesRepository userroles;

    @Autowired
    AllRolesRepository allRoles;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;
        String provider = token.getAuthorizedClientRegistrationId();
        CustomAuthenticatedPrincipal principal;

        principal = (CustomAuthenticatedPrincipal)authentication.getPrincipal();

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

            AllRoles whitelist = allRoles.getByName("whitelist");
            UserRoles ur = new UserRoles();
            ur.setRole(whitelist);
            ur.setUser(u);
            userroles.save(ur);

            System.out.println("User created with id: " + principal.getId() + " for provider: " + provider);
        }

        response.sendRedirect("/");
    }
}
