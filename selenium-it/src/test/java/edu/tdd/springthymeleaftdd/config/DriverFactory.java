package edu.tdd.springthymeleaftdd.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DriverFactory {

    protected WebDriver driver;

    @Value("${selenium.driver.chrome}")
    protected String chromeDriverPath;

    @Value("${selenium.driver.firefox}")
    protected String firefoxDriverPath;

    @Value("${selenium.driver.ie}")
    protected String ieDriverPath;

    @Value("${selenium.driver.browser}")
    public String browser;

    @SuppressWarnings("deprecation")
    public WebDriver getDriver() {

        try {
            switch (browser) {

                case "firefox":
                    // code
                    if (null == driver) {
                        System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
                        driver = new FirefoxDriver();
                    }
                    break;

                case "chrome":
                    // code
                    if (null == driver) {
                        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                        // CHROME OPTIONS
                        driver = new ChromeDriver();
                        driver.manage().window().maximize();
                        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Unable to load browser: " + e.getMessage());
        }
        /*finally {
            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        }*/
        return driver;
    }
}
