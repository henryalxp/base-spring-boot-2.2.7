package com.aflac.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import com.aflac.demo.domain.model.Claim;
import com.aflac.demo.persistence.dynamodb.repository.ClaimRepository;
import com.aflac.demo.util.HeaderSettingRequestCallback;
import com.aflac.demo.util.ResponseResults;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Ignore
public class SpringIntegrationTest {

  protected static ResponseResults latestResponse = null;

  protected RestTemplate restTemplate = new RestTemplate();

  @LocalServerPort
  protected int port;

  @Autowired
  private DynamoDBMapper dynamoDBMapper;

  @Autowired
  private AmazonDynamoDB amazonDynamoDB;

  @Autowired
  private ClaimRepository repository;

  @PostConstruct
  public void setup() {
    // XXX move DynamoDb set up to a isolated component
    try {
      CreateTableRequest tableRequest =
          dynamoDBMapper.generateCreateTableRequest(Claim.class);

      tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

      amazonDynamoDB.createTable(tableRequest);

      Claim claim = new Claim();
      claim.setDescription("default description");
      repository.save(claim);


    } catch (ResourceInUseException e) {
      // Do nothing, table already created
      // XXX use a proper logging method
      e.printStackTrace();
    }

  }

  protected void executeGet(String url) throws IOException {
    final Map<String, String> headers = new HashMap<>();
    headers.put("Accept", "application/json");
    final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
    final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

    restTemplate.setErrorHandler(errorHandler);
    latestResponse =
        restTemplate.execute("http://localhost:" + port + url, HttpMethod.GET, requestCallback,
            new ResponseExtractor<ResponseResults>() {
              @Override
              public ResponseResults extractData(ClientHttpResponse response) throws IOException {
                if (errorHandler.hadError) {
                  return (errorHandler.getResults());
                } else {
                  return (new ResponseResults(response));
                }
              }
            });

  }

  protected void executePost(String url) throws IOException {
    final Map<String, String> headers = new HashMap<>();
    headers.put("Accept", "application/json");
    final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
    final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

    if (restTemplate == null) {
      restTemplate = new RestTemplate();
    }

    restTemplate.setErrorHandler(errorHandler);
    latestResponse = restTemplate.execute(url, HttpMethod.POST, requestCallback,
        new ResponseExtractor<ResponseResults>() {
          @Override
          public ResponseResults extractData(ClientHttpResponse response) throws IOException {
            if (errorHandler.hadError) {
              return (errorHandler.getResults());
            } else {
              return (new ResponseResults(response));
            }
          }
        });

  }

  private class ResponseResultErrorHandler implements ResponseErrorHandler {
    private ResponseResults results = null;
    private Boolean hadError = false;

    private ResponseResults getResults() {
      return results;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
      hadError = response.getRawStatusCode() >= 400;
      return hadError;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
      results = new ResponseResults(response);
    }
  }

}
