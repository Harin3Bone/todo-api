package com.mock.todo.cucumber;

import com.mock.todo.TodoServiceApplication;
import com.mock.todo.config.properties.AuthProperties;
import com.mock.todo.service.impl.CardServiceImpl;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@CucumberContextConfiguration
@ContextConfiguration(
        initializers = {CucumberInitializer.class},
        classes = {CucumberBaseStepDef.Config.class}
)
@SpringBootTest(classes = TodoServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberBaseStepDef {

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ResponseEntity<String> responseEntity;

    @TestConfiguration
    static class Config {
        @InjectMocks
        CardServiceImpl cardService;
    }

    private HttpEntity<String> getAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(authProperties.getKeyName(), authProperties.getAuthValue());
        return new HttpEntity<>(headers);
    }

    @Before
    public void setup() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
    }

    @When("the client call {string} path {string}")
    public void clientCallRequest(String method, String path) {
        responseEntity = testRestTemplate.exchange(path, HttpMethod.valueOf(method), getAuthEntity(), String.class);
    }

    @Then("the client receive status code of {int}")
    public void theClientReceiveStatusCodeOf(int expectedStatus) {
        String errMsg = "Unexpected response status code: " + responseEntity.getStatusCode();
        assertEquals(expectedStatus, responseEntity.getStatusCodeValue(), errMsg);
    }

    @And("the client receive result are as follows")
    public void theClientReceiveResultAs(String expectedResponse) {
        assertEquals(expectedResponse, responseEntity.getBody());
    }

}
