package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pages.MagentoMainPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.Driver;

import java.io.IOException;
import java.util.List;

public class MagentoSteps {

    WebDriver driver = Driver.getDriver();
    MagentoMainPage magentoMainPage = new MagentoMainPage();

    @Given("user navigates to magento app")
    public void user_navigates_to_magento_app() {
        driver.get(ConfigReader.getProperty("MagentoURL"));
    }

    @When("user searches for keyword {string}")
    public void user_searches_for_keyword(String keyword) {
        magentoMainPage.searchBox.sendKeys(keyword + Keys.ENTER);
    }

    @Then("user verifies item names contain keywords")
    public void user_verifies_item_names_contain_keywords(DataTable dataTable) {
        BrowserUtils.selectByValue(magentoMainPage.limiter, "24");
        List<String> keywordsList = dataTable.asList();
        for (int i=0 ; i<magentoMainPage.productNames.size() ; i++){
            boolean isFound = false;
            String productName = magentoMainPage.productNames.get(i).getText().toLowerCase();
            for (int k=0 ; k<keywordsList.size() ; k++){
                if (productName.contains(keywordsList.get(k))){
                    isFound = true;
                }
            }
            Assert.assertTrue(productName, isFound);
        }
    }
}
