package edu.tdd.springthymeleaftdd.config.stepdefs;

import edu.tdd.springthymeleaftdd.config.pages.IndexPage;
import io.cucumber.java.Before;
import org.junit.jupiter.api.Assertions;
import edu.tdd.springthymeleaftdd.config.CucumberSpringContextConfiguration;
import io.cucumber.java8.En;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

public class IndexStepDef extends CucumberSpringContextConfiguration implements En {

    private IndexPage indexPage;

    @Before
    public void setup() throws IOException {
        indexPage = new IndexPage(driverFactory.getDriver());
    }

    private ResultActions resultActions;

    public IndexStepDef() {

        Given("i go to the index page", () -> {
            indexPage.goToIndexPage();
        });

        Then("I should land in the index page with the title {string}", (String title) -> {
            Assertions.assertEquals(title,indexPage.getTitle());
            indexPage.quit();
        });

    }
}
