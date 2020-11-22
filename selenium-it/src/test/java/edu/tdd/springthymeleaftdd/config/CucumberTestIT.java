package edu.tdd.springthymeleaftdd.config;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * To run cucumber test.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        plugin = {"pretty", "json:target/cucumber-report.json"}
       // extraGlue = {"springthymeleaftdd/controllers/cucumber/config"}
    )
public class CucumberTestIT {
}
