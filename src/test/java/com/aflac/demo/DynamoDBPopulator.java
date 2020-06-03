package com.aflac.demo;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.aflac.demo.domain.model.Claim;
import com.aflac.demo.domain.model.Policy;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("bdd")
public class DynamoDBPopulator {

  @Autowired
  private DynamoDBMapper dynamoDBMapper;

  @Autowired
  private AmazonDynamoDB amazonDynamoDB;

  @PostConstruct
  public void postConstruct() {
    this.populate();
  }

  public void populate() {

    try {
      createDynamoDBTables();
    } catch (ResourceInUseException e) {
      log.error("Error creating DynamoDB tables", e);
    }

  }

  private void createDynamoDBTables() {

    createDynamoTable(Claim.class);
    createDynamoTable(Policy.class);

  }

  private void createDynamoTable(Class<?> clazz) {
    CreateTableRequest policyTableRequest = dynamoDBMapper.generateCreateTableRequest(clazz);
    policyTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
    amazonDynamoDB.createTable(policyTableRequest);
  }

}
