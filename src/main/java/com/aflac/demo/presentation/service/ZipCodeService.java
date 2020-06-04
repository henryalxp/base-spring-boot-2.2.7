package com.aflac.demo.presentation.service;

import reactor.core.publisher.Mono;

public interface ZipCodeService {

  public Mono<String> getZipInformation(String zipCode);

}
