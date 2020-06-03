package com.aflac.demo.cucumber.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import com.aflac.demo.SpringIntegrationTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetClaimsSteps extends SpringIntegrationTest {

  @When("the consumer calls \\/claims")
  public void consumerCallsGetClaims() throws IOException {
    executeGet("/claims");
  }

  @Then("the consumer receives status code of {int}")
  public void consumerReceivesStatusCode(int expectedHttpStatus) throws IOException {
    assertEquals(expectedHttpStatus, latestResponse.getTheResponse().getRawStatusCode());
  }

  @And("the consumer receives a list of claims with attributes {string}")
  public void consumerReceivesAList(String attributes) {

    // XXX convert Response Body to a map of attributes
    // and compare the keys against attributes params

    System.err.println("Attributes: " + attributes);

  }

}
