package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Ma;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import pages.HRAppHomePage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class HRAppSteps {

    WebDriver driver= Driver.getDriver();
    String employeeId;
    List<Map<String,Object>> data;
    String editedName;

    @Given("user navigates to HRApp")
    public void user_navigates_to_hr_app() {
        driver.get(ConfigReader.getProperty("HRURL"));
    }
    @When("user creates employee with data")
    public void user_creates_employee_with_data(io.cucumber.datatable.DataTable dataTable) throws InterruptedException {
        // Converted data table to list of maps
        data = dataTable.asMaps(String.class, Object.class);
        HRAppHomePage hrAppHomePage=new HRAppHomePage();
        hrAppHomePage.employeeFirstNameInput.sendKeys(data.get(0).get("Full Name").toString().substring(0,data.get(0).get("Full Name").toString().indexOf(' ')));
        hrAppHomePage.employeeLastNameInput.sendKeys(data.get(0).get("Full Name").toString().substring(data.get(0).get("Full Name").toString().indexOf(' ')+1));
        hrAppHomePage.employeeEmailInput.sendKeys(data.get(0).get("Email").toString());
        hrAppHomePage.employeeJobTitleInput.sendKeys(data.get(0).get("Job ID").toString());
        hrAppHomePage.employeeSalaryInput.sendKeys(data.get(0).get("Salary").toString());
        hrAppHomePage.employeeHireDateDropdown.sendKeys(data.get(0).get("Hire Date").toString());
        Select select=new Select(hrAppHomePage.employeeDepartmentDropdown);
        select.selectByVisibleText(data.get(0).get("Department").toString());
        hrAppHomePage.addEmployeeButton.click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
        employeeId=hrAppHomePage.employeeIds.get(hrAppHomePage.employeeIds.size()-1).getText();
    }
    @When("user connects to database")
    public void user_connects_to_database() throws SQLException {
        JDBCUtils.connectToDatabase(ConfigReader.getProperty("DBURL"), ConfigReader.getProperty("DBUsername"),ConfigReader.getProperty("DBPassword"));
    }
    @Then("user validates that created employee is stored in database")
    public void user_validates_that_created_employee_is_stored_in_database() throws SQLException {
        List<Map<String,Object>> dbData=JDBCUtils.executeQuery("select * from employees where employee_id = " + employeeId );
        // dbData -> actual result
        // data -> expected result
        Assert.assertEquals(data.get(0).get("Full Name").toString(), dbData.get(0).get("first_name")+" "+dbData.get(0).get("last_name"));
        Assert.assertEquals(data.get(0).get("Email").toString(), dbData.get(0).get("email").toString());
        Assert.assertEquals(data.get(0).get("Job ID").toString(), dbData.get(0).get("job_id").toString());

        LocalDate date=LocalDate.parse(dbData.get(0).get("hire_date").toString()); // 2025-05-01
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("MM/dd/YYYY"); // formatter
        String formattedDate=date.format(formatter); // 2025-05-01  -> 05/01/2025

        Assert.assertEquals(data.get(0).get("Hire Date").toString(), formattedDate);
        Assert.assertEquals(data.get(0).get("Salary").toString(), (int)Double.parseDouble(dbData.get(0).get("salary").toString()) +"");
    }

    @When("user deletes created employee in UI")
    public void user_deletes_created_employee_in_ui() throws InterruptedException {
        HRAppHomePage hrAppHomePage=new HRAppHomePage();
        JavascriptExecutor js = (JavascriptExecutor)(driver);
        js.executeScript("window.scrollBy(0,12000)");
        Thread.sleep(2000);
        hrAppHomePage.deleteButtons.get(hrAppHomePage.deleteButtons.size()-1).click();
        Alert alert=driver.switchTo().alert();
        alert.accept();
        Thread.sleep(5000);
    }
    @Then("user validates that deleted employee is removed from database")
    public void user_validates_that_deleted_employee_is_removed_from_database() throws SQLException {
        List<Map<String,Object>> dbData = JDBCUtils.executeQuery("select * from employees where employee_id="+employeeId);
        Assert.assertTrue(dbData.isEmpty());
    }

    @When("user updated employee name to {string}")
    public void user_update_employee_name_to(String editedName) throws InterruptedException {
        HRAppHomePage hrAppPage = new HRAppHomePage();
        JavascriptExecutor js = ( JavascriptExecutor)(driver);
        js.executeScript("window.scrollBy(0,10000)");
        Thread.sleep(2000);
        hrAppPage.editButtons.get(hrAppPage.editButtons.size()-1).click();
        Thread.sleep(2000);
        hrAppPage.editEmpFirstName.clear();
        hrAppPage.editEmpFirstName.sendKeys(editedName.substring(0,editedName.indexOf(' ')));
        hrAppPage.editEmpLastName.clear();
        hrAppPage.editEmpLastName.sendKeys(editedName.substring(editedName.indexOf(' ')+1));
        hrAppPage.editEmpHireDate.sendKeys("05052025");
        Thread.sleep(2000);
        hrAppPage.updateButton.click();
        Thread.sleep(5000);
        this.editedName=editedName;
        // editedName = hrAppPage.employeeNames.get(hrAppPage.employeeNames.size()-1).getText().toString();
    }

    @Then("user validates employee name updated in Database")
    public void user_validates_employee_name_updated_in_database() throws SQLException {
        System.out.println(employeeId);
        List<Map<String,Object>> dbData = JDBCUtils.executeQuery("select * from employees where employee_id = "+employeeId);
        Assert.assertEquals(editedName,
                dbData.get(0).get("first_name")+" "+dbData.get(0).get("last_name"));
    }

}
