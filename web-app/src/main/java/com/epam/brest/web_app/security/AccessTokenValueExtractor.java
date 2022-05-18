package com.epam.brest.web_app.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@Slf4j
@AllArgsConstructor
public class AccessTokenValueExtractor {

    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;

    public static String getAccessTokenValue (OAuth2AuthorizedClientService auth2AuthorizedClientService) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client = auth2AuthorizedClientService
                .loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        if (client != null) {
            return client.getAccessToken().getTokenValue();
        } else {
            return null;
        }
    }
}
