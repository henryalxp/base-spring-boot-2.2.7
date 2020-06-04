package com.aflac.demo.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "amazon.dynamodb")
public class DynamoDBProperties {

  private String endpoint;

}
