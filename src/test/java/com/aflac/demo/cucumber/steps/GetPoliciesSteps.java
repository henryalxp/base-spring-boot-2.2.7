package com.aflac.demo.cucumber.steps;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import com.aflac.demo.TestHttpClient;
import com.aflac.demo.domain.model.Policy;
import com.aflac.demo.persistence.dynamodb.repository.PolicyRepository;
import io.cucumber.java.en.When;

public class GetPoliciesSteps {

  @Autowired
  private TestHttpClient httpClient;

  @Autowired
  private PolicyRepository policyRepository;

  @When("the consumer calls \\/policies")
  public void consumerCallsGetPolicies() throws IOException {

    Policy policy = new Policy();
    policy.setDescription("default description");
    policy.setStatus("FINISHED");
    policy.setPolicyNumber("123");
    policyRepository.save(policy);

    httpClient.executeGet("/policies");

  }


}
