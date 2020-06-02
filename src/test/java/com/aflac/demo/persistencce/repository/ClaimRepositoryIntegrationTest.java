package com.aflac.demo.persistencce.repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.aflac.demo.LocalDbCreationRule;
import com.aflac.demo.domain.model.Claim;
import com.aflac.demo.persistence.dynamodb.repository.ClaimRepository;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;

@RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest(classes = DemoApplication.class)
// @WebAppConfiguration
// @ActiveProfiles("local")
// @TestPropertySource(properties = {
// "amazon.dynamodb.endpoint=http://localhost:8000/",
// "amazon.aws.accesskey=test1",
// "amazon.aws.secretkey=test231"})
// @RunWith(JUnit4.class)
public class ClaimRepositoryIntegrationTest {

  private static final String DEFAULT_DESCRIPTION = "first claim";

  @ClassRule
  public static LocalDbCreationRule dynamoDB = new LocalDbCreationRule();

  private static DynamoDBMapper dynamoDBMapper;
  private static AmazonDynamoDB amazonDynamoDB;

  private ClaimRepository repository;

  private static final String DYNAMODB_ENDPOINT = "amazon.dynamodb.endpoint";
  private static final String AWS_ACCESSKEY = "amazon.aws.accesskey";
  private static final String AWS_SECRETKEY = "amazon.aws.secretkey";

  @BeforeClass
  public static void setupClass() {
    Properties testProperties = loadFromFileInClasspath("test.properties")
        .filter(properties -> !isEmpty(properties.getProperty(AWS_ACCESSKEY)))
        .filter(properties -> !isEmpty(properties.getProperty(AWS_SECRETKEY)))
        .filter(properties -> !isEmpty(properties.getProperty(DYNAMODB_ENDPOINT)))
        .orElseThrow(
            () -> new RuntimeException("Unable to get all of the required test property values"));

    String amazonAWSAccessKey = testProperties.getProperty(AWS_ACCESSKEY);
    String amazonAWSSecretKey = testProperties.getProperty(AWS_SECRETKEY);
    String amazonDynamoDBEndpoint = testProperties.getProperty(DYNAMODB_ENDPOINT);

    amazonDynamoDB =
        new AmazonDynamoDBClient(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
    amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
    dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
  }

  @Before
  public void setup() {
    try {
      repository = new ClaimRepository();
      repository.setMapper(dynamoDBMapper);

      CreateTableRequest tableRequest =
          dynamoDBMapper.generateCreateTableRequest(Claim.class);

      tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

      amazonDynamoDB.createTable(tableRequest);
    } catch (ResourceInUseException e) {
      // Do nothing, table already created
    }

    // TODO How to handle different environments. i.e. AVOID deleting all entries in Claim on
    // table
    dynamoDBMapper.batchDelete((List<Claim>) repository.findAll());
  }

  @Test
  public void getClaims() {

    Claim claim = new Claim();
    claim.setDescription(DEFAULT_DESCRIPTION);
    repository.save(claim);

    List<Claim> result = (List<Claim>) repository.findAll();
    System.out.println("*** >>" + result.iterator().next());
  }

  private static boolean isEmpty(String inputString) {
    return inputString == null || "".equals(inputString);
  }

  private static Optional<Properties> loadFromFileInClasspath(String fileName) {

    InputStream stream = null;

    try {
      Properties config = new Properties();
      Path configLocation = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
      stream = Files.newInputStream(configLocation);
      config.load(stream);
      return Optional.of(config);
    } catch (Exception e) {
      return Optional.empty();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
        }
      }
    }

  }

}
