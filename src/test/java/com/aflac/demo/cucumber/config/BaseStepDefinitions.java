package com.aflac.demo.cucumber.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.aflac.demo.HttpClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("bdd")
public class BaseStepDefinitions {

  @Autowired
  private HttpClient httpClient;

  @Then("the consumer receives status code of {int}")
  public void consumerReceivesStatusCode(int expectedHttpStatus) throws IOException {
    assertEquals(expectedHttpStatus,
        httpClient.getLatestResponse().getTheResponse().getRawStatusCode());
  }

  @And("the consumer receives a list of objects with attributes {string}")
  public void consumerReceivesAList(String expectedAttributes) throws Exception {

    ObjectMapper mapper = new ObjectMapper();
    List<Map<String, String>> result = mapper
        .readValue(
            httpClient.getLatestResponse().getBody(),
            new TypeReference<List<Map<String, String>>>() {});

    String returnedAttributes = result.get(0).keySet().stream().collect(Collectors.joining(","));

    assertEquals(expectedAttributes, returnedAttributes);

  }

}
