package com.inventivum.resourceserver.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {

    // Adding @SecurityRequirements will disable security in swagger for method it is applied to
    @GetMapping
    public Map<String, Object> getToken(@AuthenticationPrincipal Jwt jwt) {
        return Collections.singletonMap("principle", jwt);
    }

    @GetMapping("/value")
    public String getTokenValue(@AuthenticationPrincipal Jwt jwt) {
        return "Spring Resource Server Response - JWT Token: " + jwt.getTokenValue();
    }
}
