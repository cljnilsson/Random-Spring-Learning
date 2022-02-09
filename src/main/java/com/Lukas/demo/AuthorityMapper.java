package com.Lukas.demo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class AuthorityMapper implements GrantedAuthoritiesMapper
{

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities)
    {
        System.out.println("IT DOES HAPPEN WOOOO");
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
        authorities.forEach(a -> {
            if(OidcUserAuthority.class.isInstance(a)) {
                OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)a;
                OidcIdToken idToken = oidcUserAuthority.getIdToken();
                OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
            } else if(OAuth2UserAuthority.class.isInstance(a)) {
                OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)a;

                Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
            }

            // The roles given here are used for the web security but is not displayed in Principal authority for some reason.
            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER36"));
        });
        System.out.println(mappedAuthorities);
        return mappedAuthorities;
    }
}
