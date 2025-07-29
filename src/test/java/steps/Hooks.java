package steps;

import io.cucumber.java.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import utilities.Driver;

public class Hooks {

    private WebDriver driver;

    @Before
    public void setup(Scenario scenario){
        if(scenario.getSourceTagNames().contains("@api")) return;
        driver = Driver.getDriver();
        System.out.println("Before Scenario Method");
    }

    @After
    public void teardown(Scenario scenario){
        if(scenario.getSourceTagNames().contains("@api")) return;
        driver.quit();
        System.out.println("After Scenario Method");
    }

}
