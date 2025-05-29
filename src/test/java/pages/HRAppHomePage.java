package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class HRAppHomePage {

    public HRAppHomePage(){
        WebDriver driver= Driver.getDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="empFirstName")
    public WebElement employeeFirstNameInput;

    @FindBy(id="empLastName")
    public WebElement employeeLastNameInput;

    @FindBy(id="empEmail")
    public WebElement employeeEmailInput;

    @FindBy(id="empJobId")
    public WebElement employeeJobTitleInput;

    @FindBy(id="empDept")
    public WebElement employeeDepartmentDropdown;

    @FindBy(id="empHireDate")
    public WebElement employeeHireDateDropdown;

    @FindBy(id="empSalary")
    public WebElement employeeSalaryInput;

    @FindBy(xpath = "//button[text()='Add Employee']")
    public WebElement addEmployeeButton;

    @FindBy(xpath = "//tbody[@id='empTableBody']/tr/td[1]")
    public List<WebElement> employeeIds;

    @FindBy(xpath = "//tbody[@id='empTableBody']/tr/td[8]/button[text()='Delete']")
    public List<WebElement> deleteButtons;

    @FindBy(xpath = "//tbody[@id='empTableBody']/tr/td[8]/button[text()='Edit']")
    public List<WebElement> editButtons;

    @FindBy(id = "editEmpFirstName")
    public WebElement editEmpFirstName;

    @FindBy(id = "editEmpLastName")
    public WebElement editEmpLastName;

    @FindBy(id = "editEmpHireDate")
    public WebElement editEmpHireDate;

    @FindBy(xpath = "//form[@id='editEmployeeForm']//div//button[text()='Update']")
    public WebElement updateButton;

    @FindBy(xpath = "//tbody[@id='empTableBody']/tr/td[2]")
    public List<WebElement> employeeNames;

    @FindBy(xpath = "//tbody[@id='empTableBody']/tr/td[2]")
    public WebElement employeeEditedName;

}
