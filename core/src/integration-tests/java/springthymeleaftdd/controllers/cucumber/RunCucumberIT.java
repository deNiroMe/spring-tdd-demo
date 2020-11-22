package springthymeleaftdd.controllers.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * To run cucumber test.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/integration-tests/resources/features",
        plugin = { "pretty",
                    "json:target/cucumber.json",
                    "html:target/cucumber-reports.html" }
       // extraGlue = {"springthymeleaftdd/controllers/cucumber/config"}
    )
public class RunCucumberIT {
}
