package com.mock.todo.cucumber;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

public class CucumberInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String MYSQL_IMAGE = "mysql:8.0.31";
    private static final MySQLContainer<?> mySqlContainer = new MySQLContainer<>(MYSQL_IMAGE);

    private static void initTestContainers() {
        mySqlContainer.withUsername("todo_username");
        mySqlContainer.withPassword("todo_password");
        mySqlContainer.withDatabaseName("todo");
        mySqlContainer.withInitScript("sql/schema.sql");
        mySqlContainer.start();
    }

    private static void initScript() {
        JdbcDatabaseDelegate delegate = new JdbcDatabaseDelegate(mySqlContainer, "");
        ScriptUtils.runInitScript(delegate, "sql/card_data.sql");
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {

        // Initialize and start test containers
        initTestContainers();
        initScript();

        // Inject containers' hostname and port into application context
        TestPropertyValues values = TestPropertyValues.of(
                "spring.datasource.url=" + mySqlContainer.getJdbcUrl(),
                "spring.datasource.username=" + mySqlContainer.getUsername(),
                "spring.datasource.password=" + mySqlContainer.getPassword()
        );

        values.applyTo(configurableApplicationContext);
    }

}
