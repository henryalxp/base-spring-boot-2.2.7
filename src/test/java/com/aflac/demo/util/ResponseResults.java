package com.aflac.demo.util;

import org.springframework.web.reactive.function.client.ClientResponse;

public class ResponseResults {

  private final ClientResponse response;
  private final String body;

  public ResponseResults(final ClientResponse response) {
    this.response = response;
    this.body = response.bodyToMono(String.class).block();
  }

  public ClientResponse getResponse() {
    return response;
  }

  public String getBody() {
    return body;
  }

}
