package com.inventivum.resourceserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SpringDocsConfig {

    private static final String OAUTH_SCHEME_NAME = "oAuth";
    private static final String PROTOCOL_URL_FORMAT = "%s/realms/%s/protocol/openid-connect";

    @Bean
    public OpenAPI reperioAPI(KeycloakConfig config) {
        return new OpenAPI()
                .info(new Info().title("Resource Server Sample 1.0")
                        .description("Resource Server Sample")
                        .version("v1.0.0"))
                .components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme(config)))
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME));
    }

    private SecurityScheme createOAuthScheme(KeycloakConfig config) {
        OAuthFlows flows = createOAuthFlows(config);

        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
    }

    private OAuthFlows createOAuthFlows(KeycloakConfig config) {
        OAuthFlow flow = createAuthorizationCodeFlow(config);

        return new OAuthFlows()
                .authorizationCode(flow);
    }

    private OAuthFlow createAuthorizationCodeFlow(KeycloakConfig config) {
        var protocolUrl = String.format(PROTOCOL_URL_FORMAT, config.getServerUrl(), config.getRealm());

        return new OAuthFlow()
                .authorizationUrl(protocolUrl + "/auth")
                .tokenUrl(protocolUrl + "/token")
                .scopes(new Scopes()
                        .addString("openid", "")
                        .addString("profile", ""));
    }

}
