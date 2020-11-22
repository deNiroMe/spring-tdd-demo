package springthymeleaftdd.controllers.cucumber;


import edu.tdd.springthymeleaftdd.SpringThymeleafTddApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

/**
* Class to use spring application context while running cucumber
*/
@Slf4j
@AutoConfigureMockMvc
@CucumberContextConfiguration
@ActiveProfiles("h2")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SpringThymeleafTddApplication.class, loader = SpringBootContextLoader.class)
@TestPropertySource(locations = "classpath:it-application.properties")
public class CucumberSpringContextConfiguration {

    /**
     * Need this method so the cucumber
     * will recognize this class as glue and load spring context configuration
     */
    @BeforeEach
    public void setUp() {
        log.info("-- Spring Context Initialized For Executing Cucumber Tests --");
    }

}
