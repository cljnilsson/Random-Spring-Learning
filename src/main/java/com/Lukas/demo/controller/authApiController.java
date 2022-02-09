package com.Lukas.demo.controller;

import com.Lukas.demo.CryptoAuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Validated
public class authApiController
{
    @GetMapping("/user")
    public Map<String, String> user(@AuthenticationPrincipal OAuth2User principal) {
        CryptoAuthenticatedPrincipal iPrincipal = (CryptoAuthenticatedPrincipal)principal;
        System.out.println(principal.getAttributes());
        HashMap<String, String> map;
        if(principal != null) { // In theory this should never fail but for the sake of not crashing..
            //System.out.println(principal.getAttributes());
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
