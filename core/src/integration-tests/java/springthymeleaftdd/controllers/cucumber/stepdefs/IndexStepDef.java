package springthymeleaftdd.controllers.cucumber.stepdefs;

import io.cucumber.java8.En;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springthymeleaftdd.controllers.cucumber.CucumberSpringContextConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexStepDef extends CucumberSpringContextConfiguration implements En {

    @Value("${msg.title}")
    private String title;

    @Autowired
    private MockMvc mockMvc;

    private ResultActions resultActions;

    public IndexStepDef() {

        Given("i want to go to the index page", () -> {
            System.out.println(title);
            Assertions.assertEquals(true,true);
        });

        When("I call the root url {string} or the {string} url", (String url1,String url2) -> {
           resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url1));
        });

        Then("a should get a response of status code {int} for the index url", (Integer status) -> {
            resultActions.andExpect(status().is(status));
        });

        And("get redirected to the {string} page", (String page) -> {
            resultActions
                    .andExpect(view().name(page))
                    .andExpect(model().attributeExists("title"));
        });

    }
}
