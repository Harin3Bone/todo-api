package com.mock.todo.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "authorization")
public class ApiKeyProperties {

    private String keyName;
    private String authValue;
}
