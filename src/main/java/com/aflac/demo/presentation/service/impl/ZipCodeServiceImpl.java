package com.aflac.demo.presentation.service.impl;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.aflac.demo.presentation.service.ZipCodeService;
import reactor.core.publisher.Mono;

@Service
public class ZipCodeServiceImpl implements ZipCodeService {

  @Value("${zipcode.url}")
  private String baseUrl;

  private WebClient webClient;

  @PostConstruct
  public void setup() {
    this.webClient = WebClient.builder()
        .baseUrl(baseUrl)
        .build();
  }

  @Override
  public Mono<String> getZipInformation(String zipCode) {
    return webClient
        .get()
        .uri("/{zipCode}", zipCode)
        .header("Accept", "application/json")
        .retrieve()
        .bodyToMono(String.class);
  }



}
