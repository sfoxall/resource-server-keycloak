package com.inventivum.resourceserver.controller;

import com.inventivum.resourceserver.security.RoleToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Tag(name = "Token", description = "Endpoints for managing JWT authentication tokens")
@RestController
@RequestMapping("/token")
public class TokenController {

    // Adding @SecurityRequirements will disable security in swagger for method it is applied to
    @GetMapping
    @Operation(summary = "Get JWT token", tags = { "Token" })
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getToken(JwtAuthenticationToken jwt) {
        return Collections.singletonMap("principle", jwt);
    }

    @GetMapping("/roles")
    @PostAuthorize("returnObject.contains('Admin')")
    @Operation(summary = "Get account role from role service", tags = { "Token" })
    @ResponseStatus(HttpStatus.OK)
    public List<String> getRoles(JwtAuthenticationToken jwt) {
        if (jwt instanceof RoleToken) {
           return ((RoleToken)jwt).getRoles();
        }
        return new ArrayList<>();
    }

    @GetMapping("/realm/roles")
    @Operation(summary = "Get realm roles from Keycloak", tags = { "Token" })
    @ResponseStatus(HttpStatus.OK)
    public List<String> getRealmRoles(JwtAuthenticationToken jwt) {
        return jwt
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    @GetMapping("/value")
    @Operation(summary = "Get access token value value", tags = { "Token" })
    @ResponseStatus(HttpStatus.OK)
    public String getTokenValue(JwtAuthenticationToken jwt) {
        return jwt.getToken().getTokenValue();
    }
}
