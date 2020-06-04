package com.aflac.demo.cucumber.steps;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import com.aflac.demo.TestHttpClient;
import com.aflac.demo.domain.model.Claim;
import com.aflac.demo.persistence.dynamodb.repository.ClaimRepository;
import io.cucumber.java.en.When;

public class GetClaimsSteps {

  @Autowired
  private TestHttpClient httpClient;

  @Autowired
  private ClaimRepository claimRepository;

  @When("the consumer calls \\/claims")
  public void consumerCallsGetClaims() throws IOException {

    Claim claim = new Claim();
    claim.setDescription("default description");
    claim.setStatus("FINISHED");
    claimRepository.save(claim);

    httpClient.executeGet("/claims");

  }



}
