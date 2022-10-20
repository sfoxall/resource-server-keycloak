package com.inventivum.resourceserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "keycloak")
@Configuration
@Getter
@Setter
public class KeycloakConfig {
    private String serverUrl;
    private String realm;
}


