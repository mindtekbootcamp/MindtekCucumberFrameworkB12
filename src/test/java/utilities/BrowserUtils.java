package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@SuppressWarnings("StringTemplateMigration")
public class BrowserUtils {

    /**
     * This method accepts first name and last name as String values,
     * and generates an email with those values.
     * @param firstName
     * @param lastName
     * @return
     */
    public static String randomEmailGenerator(String firstName, String lastName){
        Random random = new Random();
        int randomNum = random.nextInt(99999);
        return firstName+"."+lastName+randomNum+"@fakemail.com";
    }

    /**
     * This method uses UUID to generate a random email.
     * @return
     */
    public static String uuidEmailGenerator(){
        UUID uuid = UUID.randomUUID();
        return "email"+uuid+"@fakemail.com";
    }

    /**
     * This method creates Select object and selects an option by its value.
     * @param element
     * @param value
     */
    public static void selectByValue(WebElement element, String value){
        Select select = new Select(element);
        select.selectByValue(value);
    }

    /**
     * This method takes a screenshot and saves it in resources package.
     * @param fileName
     * @throws IOException
     */
    public static void takeScreenshot(String fileName) throws IOException {
        WebDriver driver = Driver.getDriver();
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = "src/test/resources/screenshots/"+fileName+".png";
        File file = new File(path);
        FileUtils.copyFile(screenshot, file);
    }

    /**
     * This method waits for element to be visible.
     * @param element
     */
    public static void waitForElementToBeVisible(WebElement element){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * This method waits for element to be clickable.
     * @param element
     */
    public static void waitForElementToBeClickable(WebElement element){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 15);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Scrolls to the bottom of the page
     */
    public static void scrollToBottom(){
        JavascriptExecutor jse = ((JavascriptExecutor) Driver.getDriver());
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

}
