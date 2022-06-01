package com.epam.brest.web_app.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        loadResources().stream()
                .map(this::loadYaml)
                .forEach(environment.getPropertySources()::addLast);
    }

    private PropertySource<?> loadYaml(Resource path) {
        if (!path.exists()) {
            throw new IllegalArgumentException("Resource " + path + " does not exist");
        }
        try {
            return this.loader.load(path.getFilename(), path).get(0);
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Failed to load yaml configuration from " + path, ex);
        }
    }

    private List<Resource> loadResources() {
        try {
            return Arrays.asList(ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                    .getResources("classpath:application-dev.yaml"));
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to read yaml configuration file", e);
        }
    }

}