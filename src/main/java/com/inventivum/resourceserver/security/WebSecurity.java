package com.inventivum.resourceserver.security;

import com.inventivum.resourceserver.service.RoleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity {

    private final RoleService roleService;
    private final AuthenticationEntryPoint authEntryPoint;

    public WebSecurity(RoleService roleService, @Qualifier("delegatedAuthenticationEntryPoint") AuthenticationEntryPoint authEntryPoint) {
        this.roleService = roleService;
        this.authEntryPoint = authEntryPoint;
    }

    public Converter<Jwt, Collection<GrantedAuthority>> authoritiesConverter() {
        // This is a converter for roles as embedded in the JWT by a Keycloak server
        // Roles are taken from both realm_access.roles
        return jwt -> {
            final var realmAccess = (Map<String, Object>) jwt.getClaims().getOrDefault("realm_access", Map.of());

            return ((List<String>) realmAccess.get("roles"))
                    .stream()
                    .map(r -> "ROLE_" + r)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public Converter<Jwt,AbstractAuthenticationToken> customJwtAuthenticationConverter(RoleService roleService) {
        return new CustomJwtAuthenticationConverter(
                authoritiesConverter(), roleService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ServerProperties serverProperties)
            throws Exception {

        // Enable OAuth2 with custom authorities mapping
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(customJwtAuthenticationConverter(roleService));

        // Enable anonymous
        http.anonymous();

        // Enable and configure CORS
        http.cors().configurationSource(corsConfigurationSource());

        // State-less session (state in access-token only)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Enable CSRF with cookie repo because of state-less session-management
        http.csrf().disable();

        // Using a delegated authentication entry point to forward to controller advice
        http.exceptionHandling().authenticationEntryPoint(authEntryPoint);

        // Using a custom handler for forbidden 403, as not picked-up in entry point above
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

        // If SSL enabled, disable http (https only)
        if (serverProperties.getSsl() != null && serverProperties.getSsl().isEnabled()) {
            http.requiresChannel().anyRequest().requiresSecure();
        } else {
            http.requiresChannel().anyRequest().requiresInsecure();
        }

        // Route security: authenticated to all routes but Swagger-UI
        // @formatter:off
        http.authorizeRequests()
                .antMatchers( "/v3/api-docs/**").permitAll()
                .antMatchers( "/swagger-ui/**").permitAll()
                .antMatchers("/swagger-ui/index.html").permitAll()
                .antMatchers("/**").hasAnyRole("admin", "user")
                .anyRequest().authenticated();
        // @formatter:on

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        // Very permissive CORS config...
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));

        // Limited to API routes (neither actuator nor Swagger-UI)
        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

