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
        System.out.println("IT DOES HAPPEN WOOOO");
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
        authorities.forEach(a -> {
            if(OidcUserAuthority.class.isInstance(a)) {
                OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)a;
                OidcIdToken idToken = oidcUserAuthority.getIdToken();
                OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
            } else if(OAuth2UserAuthority.class.isInstance(a)) {
                // Unfinished, it should check for both id and provider
                OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)a;
                Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

                User u = users.findOneByOauth(userAttributes.get("id").toString());
                if(u != null) {
                    System.out.println(u.getRoles().size());
                    for(UserRoles role : u.getRoles()) {
                        System.out.println("Added: " + "ROLE_" + role.getRole().toUpperCase());
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().toUpperCase()));
                    }
                }
            }
        });
        System.out.println(mappedAuthorities);
        return mappedAuthorities;
    }
}
