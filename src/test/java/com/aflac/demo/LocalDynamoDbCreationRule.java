package com.aflac.demo;

import org.junit.rules.ExternalResource;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;

public class LocalDynamoDbCreationRule extends ExternalResource {

  private DynamoDBProxyServer server;

  public LocalDynamoDbCreationRule() {
    System.setProperty("sqlite4java.library.path", "native-libs");
  }

  @Override
  public void before() throws Exception {
    String port = "8000";
    server = ServerRunner.createServerFromCommandLineArgs(
        new String[] {"-inMemory", "-port", port});
    server.start();
  }

  @Override
  public void after() {
    this.stopUnchecked(server);
  }

  protected void stopUnchecked(DynamoDBProxyServer dynamoDbServer) {
    try {
      dynamoDbServer.stop();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
