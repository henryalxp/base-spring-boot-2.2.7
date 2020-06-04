package com.aflac.demo;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import com.aflac.demo.util.ResponseResults;

@Component
@Profile("bdd")
@Scope(SCOPE_CUCUMBER_GLUE)
public class TestHttpClient {

  private static final String LOCAL_BASE = "http://localhost:";

  private ResponseResults latestResponse;

  private WebClient webClient;

  @LocalServerPort
  protected int port;

  @PostConstruct
  public void setup() {
    this.webClient = WebClient.builder()
        .baseUrl(LOCAL_BASE + port)
        .build();
  }

  public ResponseResults getLatestResponse() {
    return latestResponse;
  }

  public void executeGet(String uri) throws IOException {

    latestResponse = new ResponseResults(webClient
        .get()
        .uri(uri)
        .header("Accept", "application/json")
        .exchange()
        .block());

  }

  public void executeGet(String uri, Map<String, List<String>> params) throws IOException {

    latestResponse = new ResponseResults(webClient
        .get()
        .uri(
            uribuilder -> uribuilder
                .path(uri)
                .queryParams(new LinkedMultiValueMap<String, String>(params))
                .build())
        .header("Accept", "application/json")
        .exchange()
        .block());

  }

}
