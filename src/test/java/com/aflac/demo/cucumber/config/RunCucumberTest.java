package com.aflac.demo.cucumber.config;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import com.aflac.demo.LocalDynamoDbCreationRule;
import com.aflac.demo.LocalZipCodeServerCreationRule;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "html:target/cucumber"},
    features = "src/test/resources/features",
    monochrome = true,
    extraGlue = {"com.aflac.demo.cucumber.steps"})
public class RunCucumberTest {

  @ClassRule
  public static LocalDynamoDbCreationRule dynamoDB = new LocalDynamoDbCreationRule();

  @ClassRule
  public static LocalZipCodeServerCreationRule zipCodeServer = new LocalZipCodeServerCreationRule();

}
