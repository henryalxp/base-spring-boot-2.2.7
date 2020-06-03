package com.aflac.demo.cucumber;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import com.aflac.demo.LocalDbCreationRule;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@Profile("bdd")
@CucumberOptions(
    plugin = {"pretty", "html:target/cucumber"},
    features = "src/test/resources/features",
    monochrome = true,
    glue = {"com.aflac.demo.cucumber.steps"})
public class RunCucumberTest {

  @ClassRule
  public static LocalDbCreationRule dynamoDB = new LocalDbCreationRule();

}
