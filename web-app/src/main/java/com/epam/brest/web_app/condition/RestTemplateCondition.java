package com.epam.brest.web_app.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RestTemplateCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String enabledHttpClientType = context.getEnvironment().getProperty("app.httpClient");
        return (enabledHttpClientType != null && enabledHttpClientType.equalsIgnoreCase("RestTemplate"));
    }
}
