package com.aflac.demo;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import com.aflac.demo.util.HeaderSettingRequestCallback;
import com.aflac.demo.util.ResponseResults;

@Component
@Profile("bdd")
@Scope(SCOPE_CUCUMBER_GLUE)
public class HttpClient {

  protected ResponseResults latestResponse;

  // XXX replace using web client
  protected RestTemplate restTemplate = new RestTemplate();

  @LocalServerPort
  protected int port;

  public ResponseResults getLatestResponse() {
    return latestResponse;
  }

  public void executeGet(String url) throws IOException {
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

  public void executePost(String url) throws IOException {
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
