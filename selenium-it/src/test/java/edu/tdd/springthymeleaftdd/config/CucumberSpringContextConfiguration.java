package edu.tdd.springthymeleaftdd.config;


import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

/**
* Class to use spring application context while running cucumber
*/
@Slf4j
@ContextConfiguration
@CucumberContextConfiguration
@TestPropertySource(locations = "/selenium-application.properties")
@SpringBootTest(classes = {  edu.tdd.springthymeleaftdd.SpringThymeleafTddApplication.class},
                webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberSpringContextConfiguration {

    @Autowired
    protected DriverFactory driverFactory;

    /**
     * Need this method so the cucumber
     * will recognize this class as glue and load spring context configuration
     */
    @BeforeEach
    public void setUp() {
        log.info("-- Spring Context Initialized For Executing Cucumber/Selenium Tests --");
    }

}
