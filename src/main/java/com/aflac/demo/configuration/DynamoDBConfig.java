package com.aflac.demo.configuration;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;


@Configuration
@EnableDynamoDBRepositories(basePackages = "com.aflac.demo.persistence.dynamodb.repository")
public class DynamoDBConfig {

  @Autowired
  private DynamoDBProperties dynamoDbProperties;

  @Autowired
  private AWSProperties awsProperties;

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
    AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());

    if (!StringUtils.isEmpty(dynamoDbProperties.getEndpoint())) {
      amazonDynamoDB.setEndpoint(dynamoDbProperties.getEndpoint());
    }

    return amazonDynamoDB;
  }

  @Bean
  public AWSCredentials amazonAWSCredentials() {
    return new BasicAWSCredentials(
        awsProperties.getAccessKey(),
        awsProperties.getSecretKey());
  }

}
