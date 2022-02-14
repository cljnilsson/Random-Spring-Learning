package com.Lukas.demo.controller;

import com.Lukas.demo.model.User;
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

    private ArrayList<String> getCombinedAuths(OAuth2User principal, CryptoAuthenticatedPrincipal iPrincipal) {
        ArrayList<String> all = new ArrayList<>();
        User u = users.findOneByOauthIdAndProvider(iPrincipal.getProvider(), iPrincipal.getId());
        all.addAll((Collection)principal.getAuthorities());
        ArrayList<UserRoles> first = u.getRoles();

        for(int i = 0; i < first.size(); i++) {
            all.add(first.get(i).getRole());
        }

        return all;
    }

    @GetMapping("/user")
    public Map<String, String> user(@AuthenticationPrincipal OAuth2User principal)
    {
        CryptoAuthenticatedPrincipal iPrincipal = (CryptoAuthenticatedPrincipal)principal;
        //System.out.println(principal.getAttributes());
        HashMap<String, String> map;
        if(principal != null) { // In theory this should never fail but for the sake of not crashing..
            List<UserRoles> roles = users.findOneByOauth(iPrincipal.getId()).getRoles();
            System.out.println(this.getCombinedAuths(principal, iPrincipal).toString());

            map = new HashMap<String, String>() {{
                put("name", iPrincipal.getName());
                put("avatar", iPrincipal.getImg());
                put("auth", principal.getAuthorities().toString());
            }};
        } else {
            map = new HashMap<>();
        }
        return map;
    }


}
