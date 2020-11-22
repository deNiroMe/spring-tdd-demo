package edu.tdd.springthymeleaftdd.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "/selenium-application.properties")
public class SpringThymeleafTddApplicationSelenium {
}
