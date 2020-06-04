package com.aflac.demo;

import java.io.IOException;
import org.junit.rules.ExternalResource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockWebServer;

@Slf4j
public class LocalZipCodeServerCreationRule extends ExternalResource {

  public static MockWebServer mockZipCodeBE;

  @Override
  public void before() throws Exception {
    mockZipCodeBE = new MockWebServer();
    mockZipCodeBE.start(10001);
  }

  @Override
  public void after() {
    try {
      mockZipCodeBE.shutdown();
    } catch (IOException e) {
      log.error("Error stopping zipcode server", e);
    }
  }


}
