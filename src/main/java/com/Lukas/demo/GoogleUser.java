package com.Lukas.demo;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class GoogleUser extends DefaultOidcUser implements CryptoAuthenticatedPrincipal
{
    public GoogleUser(OidcUser user) {
        super(user.getAuthorities(), user.getIdToken());
    }

    @Override
    public String getName() {
        return this.getAttribute("name");
    }

    @Override
    public String getImg()
    {
        return this.getAttribute("picture");
    }
}
