package com.Lukas.demo.controller;

import com.Lukas.demo.model.UserRoles;
import com.Lukas.demo.repository.UserRepository;
import com.Lukas.demo.service.CryptoAuthenticatedPrincipal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;

@RestController
@RequestMapping("/auth")
@Validated
public class authApiController
{
    @Autowired
    UserRepository users;

    @GetMapping("/user")
    public Map<String, String> user(@AuthenticationPrincipal OAuth2User principal)
    {
        CryptoAuthenticatedPrincipal iPrincipal = (CryptoAuthenticatedPrincipal)principal;
        System.out.println(principal.getAttributes());
        HashMap<String, String> map;
        if(principal != null) { // In theory this should never fail but for the sake of not crashing..
            List<UserRoles> roles = users.findOneByOauth(iPrincipal.getId()).getRoles();
            System.out.println(roles.get(0).getRole());

            map = new HashMap<String, String>() {{
                put("name", iPrincipal.getName());
                put("avatar", iPrincipal.getImg());
            }};
        } else {
            map = new HashMap<>();
        }
        return map;
    }


}
