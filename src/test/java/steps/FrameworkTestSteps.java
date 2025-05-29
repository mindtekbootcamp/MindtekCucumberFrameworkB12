package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import utilities.ConfigReader;
import utilities.Driver;

public class FrameworkTestSteps {

    WebDriver driver = Driver.getDriver();

    @Given("user navigates to google")
    public void user_navigates_to_google() {
        driver.get(ConfigReader.getProperty("googleURL"));
    }

    @Then("user verifies main page with title")
    public void user_verifies_main_page_with_title() {
        Assert.assertEquals("Google", driver.getTitle());
    }
}
