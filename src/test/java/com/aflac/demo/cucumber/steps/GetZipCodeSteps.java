package com.aflac.demo.cucumber.steps;

import static com.aflac.demo.LocalZipCodeServerCreationRule.mockZipCodeBE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.aflac.demo.TestHttpClient;
import com.aflac.demo.domain.dto.ZipCodeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;

@Slf4j
public class GetZipCodeSteps {

  @Autowired
  private TestHttpClient httpClient;

  @When("the consumer calls \\/zipcode and provides the code {string}")
  public void consumerCallsGetPolicies(String zipCode) throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    ZipCodeInfo mockedResponse = ZipCodeInfo.builder().country("USA").postCode(zipCode).build();
    mockZipCodeBE.enqueue(new MockResponse()
        .setBody(objectMapper.writeValueAsString(mockedResponse))
        .addHeader("Content-Type", "application/json"));

    httpClient.executeGet("/zipcode", Map.of("code", Arrays.asList(zipCode)));

  }

  @And("the consumer receives a response with "
      + "post code {string} and "
      + "country {string}")
  public void consumerReceivesAList(
      String code,
      String country) throws Exception {

    log.info("Mock request URL: " + mockZipCodeBE.takeRequest().getRequestUrl().toString());

    ObjectMapper objectMapper = new ObjectMapper();
    ZipCodeInfo info = objectMapper.readValue(
        httpClient.getLatestResponse().getBody(),
        ZipCodeInfo.class);

    assertEquals(code, info.getPostCode());
    assertEquals(country, info.getCountry());

  }

}
