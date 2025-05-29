package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class MagentoMainPage {

    WebDriver driver;

    public MagentoMainPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "search")
    public WebElement searchBox;

    @FindBy(xpath = "(//select[@id='limiter'])[2]")
    public WebElement limiter;

    @FindBy(xpath = "//a[@class='product-item-link']")
    public List<WebElement> productNames;
}
