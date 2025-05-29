package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.SmartbearLoginPage;
import pages.SmartbearOrderPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.Driver;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SuppressWarnings("StringTemplateMigration")
public class SmartbearSteps {

    WebDriver driver = Driver.getDriver();
    SmartbearLoginPage smartbearLoginPage = new SmartbearLoginPage();
    SmartbearOrderPage smartbearOrderPage = new SmartbearOrderPage();

    @Given("user navigates to smartbear app")
    public void user_navigates_to_smartbear_app() {
        driver.get(ConfigReader.getProperty("SmartBearURL"));
    }

    @When("user logs in with username {string} and password {string}")
    public void user_logs_in_with_username_and_password(String username, String password) {
        smartbearLoginPage.usernameInput.sendKeys(username);
        smartbearLoginPage.passwordInput.sendKeys(password);
        smartbearLoginPage.loginButton.click();
    }

    @Then("user lands on main page")
    public void user_lands_on_main_page() {
        Assert.assertEquals("Invalid Credentials", "Web Orders", driver.getTitle());
    }

    @Then("user gets error message {string}")
    public void user_gets_error_message(String errorMessage) {
        Assert.assertEquals(errorMessage, smartbearLoginPage.errorMessage.getText());
    }

    @When("user clicks on Order tab")
    public void user_clicks_on_order_tab() {
        smartbearOrderPage.orderTab.click();
    }

    @When("user selects product {string} and quantity {string}")
    public void user_selects_product_and_quantity(String product, String quantity) {
        BrowserUtils.selectByValue(smartbearOrderPage.productDropdown, product);
        smartbearOrderPage.quantityInput.clear();
        smartbearOrderPage.quantityInput.sendKeys(quantity);
        smartbearOrderPage.calculateBtn.click();
    }

    @Then("user validates total for product {string} and quantity {int}")
    public void user_validates_total_for_product_and_quantity(String product, Integer quantity) {
        int pricePerUnit = Integer.parseInt(smartbearOrderPage.pricePerUnit.getAttribute("value"));
        int discount = Integer.parseInt(smartbearOrderPage.discount.getAttribute("value"));
        int actualTotal = Integer.parseInt(smartbearOrderPage.total.getAttribute("value"));
        int expectedTotal = pricePerUnit*quantity ;
        int totalWithDiscount = expectedTotal-(expectedTotal*discount/100);
        if (quantity >= 10){
            System.out.println(totalWithDiscount+" - "+actualTotal);
            Assert.assertEquals(totalWithDiscount, actualTotal);
        } else {
            System.out.println(expectedTotal+" - "+actualTotal);
            Assert.assertEquals(expectedTotal, actualTotal);
        }
    }

    @When("user places an order")
    public void user_places_an_order(DataTable dataTable) throws IOException {
        List<Map<String, Object>> testData = dataTable.asMaps(String.class, Object.class);
        for (int i=0 ; i< testData.size() ; i++){
            BrowserUtils.selectByValue(smartbearOrderPage.productDropdown, testData.get(i).get("PRODUCT").toString());
            smartbearOrderPage.quantityInput.clear();
            smartbearOrderPage.quantityInput.sendKeys(testData.get(i).get("QUANTITY").toString());
            smartbearOrderPage.customerNameInput.sendKeys(testData.get(i).get("CUSTOMER NAME").toString());
            smartbearOrderPage.streetInput.sendKeys(testData.get(i).get("STREET").toString());
            smartbearOrderPage.cityInput.sendKeys(testData.get(i).get("CITY").toString());
            smartbearOrderPage.stateInput.sendKeys(testData.get(i).get("STATE").toString());
            smartbearOrderPage.zipInput.sendKeys(testData.get(i).get("ZIP").toString());
            switch (testData.get(i).get("CARD").toString()) {
                case "Visa":
                    smartbearOrderPage.visaBtn.click();
                    break;
                case "Amex":
                    smartbearOrderPage.amexBtn.click();
                    break;
                case "Master":
                    smartbearOrderPage.masterCardBtn.click();
                    break;
            }
            smartbearOrderPage.cardNumInput.sendKeys(testData.get(i).get("CARD NUM").toString());
            smartbearOrderPage.expDateInput.sendKeys(testData.get(i).get("EXP DATE").toString());
            smartbearOrderPage.processBtn.click();
            BrowserUtils.takeScreenshot("orderCreation");
        }
    }

    @Then("user validates new order with success message {string}")
    public void user_validates_new_order_with_success_message(String successMessage) {
        Assert.assertEquals(successMessage, smartbearOrderPage.successMessage.getText());
    }
}















