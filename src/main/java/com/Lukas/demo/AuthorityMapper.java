package com.Lukas.demo;

import com.Lukas.demo.model.User;
import com.Lukas.demo.model.UserRoles;
import com.Lukas.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserRepository users;

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities)
    {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
        authorities.forEach(a -> {
            User u; // Unfinished, it should check for both id and provider

            if(OidcUserAuthority.class.isInstance(a)) {
                OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)a;

                Map<String, Object> userAttributes = oidcUserAuthority.getAttributes();
                u = users.findOneByOauth(userAttributes.get("sub").toString());
            } else if(OAuth2UserAuthority.class.isInstance(a)) {
                OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)a;
                Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

                u = users.findOneByOauth(userAttributes.get("id").toString());
            } else {
                u = null; // Should never happen
            }

            if(u != null) {
                for(UserRoles role : u.getRoles()) {
                    System.out.println("Added: " + role.getRole());
                    mappedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
                }
            }

        });
        System.out.println(mappedAuthorities);
        return mappedAuthorities;
    }
}
