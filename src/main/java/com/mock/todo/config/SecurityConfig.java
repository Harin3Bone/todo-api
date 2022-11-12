package com.mock.todo.config;

import com.mock.todo.config.properties.ApiKeyProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static com.mock.todo.constants.ErrorMessage.AUTHORIZE_FAILED;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ConfigurationPropertiesScan(basePackages = "com.mock.todo.config.properties")
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            /* Actuator */
            "/actuator/health",

            /* OpenAPI */
            "/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**"
    };

    private static final String[] AUTH_BLACKLIST = {
            "/actuator/restart"
    };

    private final ApiKeyProperties apiKeyProperties;

    public SecurityConfig(ApiKeyProperties apiKeyProperties) {
        this.apiKeyProperties = apiKeyProperties;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Configure allow methods
        RequestMatcher requestMatcher = new RequestMatcher() {
            private final Pattern allowedMethods = Pattern.compile("^(GET|POST|PUT|DELETE|PATCH|HEAD|TRACE|OPTIONS)$");
            private final RegexRequestMatcher apiMatcher = new RegexRequestMatcher("/v[0-9]*/.*", null);

            @Override
            public boolean matches(HttpServletRequest request) {
                return !(allowedMethods.matcher(request.getMethod()).matches() || apiMatcher.matches(request));
            }
        };

        // Configure API-KEY Authentication
        ApiKeyAuthConfig filter = new ApiKeyAuthConfig(apiKeyProperties.getKeyName());
        filter.setAuthenticationManager(authentication -> {
            String principle = (String) authentication.getPrincipal();
            if (!apiKeyProperties.getAuthValue().equals(principle)) {
                throw new BadCredentialsException(AUTHORIZE_FAILED);
            }
            authentication.setAuthenticated(true);
            return authentication;
        });

        return http.csrf().requireCsrfProtectionMatcher(requestMatcher)
                .and().antMatcher("/**").sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(filter).authorizeHttpRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(AUTH_BLACKLIST).denyAll()
                .anyRequest().authenticated()
                .and().build();
    }

}
