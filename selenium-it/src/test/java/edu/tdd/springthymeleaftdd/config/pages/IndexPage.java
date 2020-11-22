package edu.tdd.springthymeleaftdd.config.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class IndexPage extends BasePage {

    private @FindBy(xpath = "//h1[@class='index mt-5']")
    WebElement indexTitle;

    public IndexPage(WebDriver driver) throws IOException {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void goToIndexPage() throws IOException {
        driver.get("http://localhost:8981/index");
    }

    public String getTitle() {
        return indexTitle.getText();
    }

}