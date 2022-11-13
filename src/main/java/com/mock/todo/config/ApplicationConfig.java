package com.mock.todo.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = "com.mock.todo.repository")
@ConfigurationPropertiesScan(basePackages = "com.mock.todo.config.properties")
public class ApplicationConfig {
}
