package com.inventivum.resourceserver.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

public class RoleToken extends JwtAuthenticationToken {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<String> roles;

    public RoleToken(Jwt jwt, Collection<GrantedAuthority> authorities, String name, List<String> roles) {
        super(jwt, authorities, name);
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }
}
