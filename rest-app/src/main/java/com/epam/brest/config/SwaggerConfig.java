package com.epam.brest.config;

import com.epam.brest.rest.VersionController;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    private static final String OAUTH_SCHEME_NAME = "keycloakOAuth";

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String tokenUri;

    @Bean
    public OpenAPI springSetlistOrganizerOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Setlist Organizer API")
                        .description("'Setlist Organizer' is a web application for organizing repertoires of musical bands.")
                        .version(new VersionController().version())
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0)")))
                .externalDocs(new ExternalDocumentation()
                        .description("Setlist Organizer github")
                        .url("https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer"))
                .components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME));

    }

    private SecurityScheme createOAuthScheme() {

        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .password(new OAuthFlow()
                                .tokenUrl(tokenUri)
                                .scopes(new Scopes())));

    }


}

