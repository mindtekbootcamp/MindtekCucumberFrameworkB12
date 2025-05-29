package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import pojos.Department;
import pojos.Employee;
import utilities.ConfigReader;
import utilities.JDBCUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class HRApiSteps {

    String departmentId;
    Map<String, Object> departmentData;
    String employeeID;
    Map<String,Object> employeeData;
    Map<String, Object> updatedDepartmentData;
    int statusCode;
    String errorMessage;
    int nonExistingDepartmentId;
    int numberOfDepartmentsInResponse;
    List<String> departmentNames;

    @Given("user creates department with post api call with data")
    public void user_creates_department_with_post_api_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        departmentData=dataTable.asMap(String.class, Object.class);

        Department department=new Department();
        department.setName(departmentData.get("Department name").toString());
        department.setLocation_id(departmentData.get("Location id").toString());

        Response response = given().baseUri("http://localhost:3000/api")
                .and().header("Content-Type", "application/json")
                .and().body(department) // SERIALIZATION | Converting Java Object into Json String
                .when().post("/departments");
        response.then().log().all();
        departmentId=response.getBody().jsonPath().getString("department_id");
    }
    @Then("user validates created department is stored in database")
    public void user_validates_created_department_is_stored_in_database() throws SQLException {
        JDBCUtils.connectToDatabase(ConfigReader.getProperty("DBURL"), ConfigReader.getProperty("DBUsername"), ConfigReader.getProperty("DBPassword"));
        List<Map<String,Object>> dbData = JDBCUtils.executeQuery("select * from departments where department_id="+departmentId);
        // departmentData -> expected data
        // dbData -> actual data
        Assert.assertEquals(departmentData.get("Department name").toString(), dbData.get(0).get("department_name"));
        Assert.assertEquals(departmentData.get("Location id").toString(), dbData.get(0).get("location_id"));
    }

    @Given("user creates employee with post api call with data")
    public void user_edit_employee_with_put_api_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        employeeData = dataTable.asMap(String.class, Object.class);

        Employee employee=new Employee();
        employee.setFirst_name(employeeData.get("First name").toString());
        employee.setLast_name(employeeData.get("Last name").toString());
        employee.setEmail("johnathan.does@hotmail.com");
        employee.setHire_date(employeeData.get("Hire date").toString());
        employee.setJob_id(5);
        employee.setSalary(10000.00);
        employee.setDepartment_id(10);

        Response response = given().baseUri("http://localhost:3000/api")
                .and().header("Content-Type","application/json")
                .and().body(employee)
                .when().post("/employees");

        response.then().log().all();
        employeeID = response.getBody().jsonPath().getString("employee_id");
    }
    @Then("user validates employee created in database")
    public void user_validates_employee_edited_in_database() throws SQLException {
        JDBCUtils.connectToDatabase(ConfigReader.getProperty("DBURL"),
                ConfigReader.getProperty("DBUsername"),
                ConfigReader.getProperty("DBPassword"));

        List<Map<String,Object>> dbData = JDBCUtils.executeQuery("select * from employees where employee_id= "+employeeID);
        Assert.assertEquals(employeeData.get("First name").toString(), dbData.get(0).get("first_name"));
        Assert.assertEquals(employeeData.get("Last name").toString(), dbData.get(0).get("last_name"));
    }

    @When("user updates created department with put api call with data")
    public void user_updates_created_department_with_put_api_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        updatedDepartmentData=dataTable.asMap(String.class,Object.class);
        Response response=given().baseUri("http://localhost:3000/api")
                .and().header("Content-Type","application/json")
                .and().body("{\n" +
                        "  \"name\": \""+updatedDepartmentData.get("Department name")+"\",\n" +
                        "  \"location_id\": \""+departmentData.get("Location id")+"\"\n" +
                        "}")
                .and().log().all() // Logging REQUEST details
                .when().put("/departments/"+departmentId);
        response.then().log().all(); // Logging RESPONSE details
    }

    @Then("user validates department is update in database")
    public void user_validates_department_is_update_in_database() throws SQLException {
        JDBCUtils.connectToDatabase(ConfigReader.getProperty("DBURL"),
                ConfigReader.getProperty("DBUsername"),
                ConfigReader.getProperty("DBPassword"));
        List<Map<String,Object>> dbData=JDBCUtils.executeQuery("select * from departments where department_id="+departmentId);
        // Expected Data -> updatedDepartmentData
        // Actual Data -> dbData
        Assert.assertEquals(updatedDepartmentData.get("Department name").toString(), dbData.get(0).get("department_name").toString());
    }

    @When("user deletes created department with delete api call")
    public void user_deletes_created_department_with_delete_api_call() {
        Response response=given().baseUri("http://localhost:3000/api")
                .and().log().all()
                .when().delete("/departments/"+departmentId);
        response.then().log().all();
    }
    @Then("user validates department is deleted in database")
    public void user_validates_department_is_deleted_in_database() throws SQLException {
        JDBCUtils.connectToDatabase(ConfigReader.getProperty("DBURL"),
                ConfigReader.getProperty("DBUsername"),
                ConfigReader.getProperty("DBPassword"));
        List<Map<String,Object>> dbData=JDBCUtils.executeQuery("select * from departments where department_id="+departmentId);
        // Expected Data -> 0 departments
        // Actual Data -> dbData
        Assert.assertEquals(0,dbData.size());
    }

    @Given("user creates department with post api call with invalid URL")
    public void user_creates_department_with_post_api_call_with_invalid_url() {
        Response response = given().baseUri("http://localhost:3000/api")
                .and().header("Content-Type", "application/json")
                .and().body("{\n" +
                        "  \"name\": \"Enrollment\",\n" +
                        "  \"location_id\": 2400\n" +
                        "}")
                .when().post("/department");
        response.then().log().all();
        statusCode=response.statusCode();
    }
    @Then("user validates {string} error message")
    public void user_validates_error_message(String string) {
        Assert.assertEquals(404,statusCode);
    }

    @Given("user creates department with post api call with no content type header")
    public void user_creates_department_with_post_api_call_with_no_content_type_header() {
        Response response = given().baseUri("http://localhost:3000/api")
                .and().body("{\n" +
                        "  \"name\": \"Enrollment\",\n" +
                        "  \"location_id\": 2400\n" +
                        "}")
                .when().post("/departments");
        response.then().log().all();
        statusCode=response.statusCode();
    }
    @Then("user validates {int} status code")
    public void user_validates_status_code(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode,statusCode);
    }

    @Given("user creates department with post api call with missing required fields {string} in request body")
    public void user_creates_department_with_post_api_call_with_missing_required_fields_in_request_body(String missingField) {

        String body="";

        if(missingField.equals("department_name")){
            body="{\n" +
                    "  \"location_id\": 2400\n" +
                    "}";
        }else if(missingField.equals("location_id")){
            body="{\n" +
                    "  \"name\": \"Enrollment\"\n" +
                    "}";
        }

        Response response = given().baseUri("http://localhost:3000/api")
                .header("Content-Type","application/json")
                .and().body(body)
                .when().post("/departments");
        response.then().log().all();
        statusCode=response.statusCode();
        errorMessage=response.body().jsonPath().getString("error");
    }
    @Then("user validates {string} error")
    public void user_validates_error(String expectedErrorMessage) {
        Assert.assertEquals(expectedErrorMessage,errorMessage);
    }

    @Given("user creates department with post api call with invalid data")
    public void user_creates_department_with_post_api_call_with_invalid_data(io.cucumber.datatable.DataTable dataTable) {
        departmentData = dataTable.asMap(String.class,Object.class);

        String departmentName=departmentData.get("Department name").toString();
        String locationId=departmentData.get("Location id").toString();

        // _space -> " ";
        // _empty -> "";

        if(departmentName.equals("_empty")) departmentName="";
        else if(departmentName.equals("_space")) departmentName=" ";

        if(locationId.equals("_empty")) locationId="";
        else if(locationId.equals("_space")) locationId=" ";

        String body = "{\n" +
                "  \"name\": \""+departmentName+"\",\n" +
                "  \"location_id\": \""+locationId+"\"\n" +
                "}";
        Response response = given().baseUri("http://localhost:3000/api")
                .header("Content-Type","application/json")
                .and().body(body)
                .when().post("/departments");
        response.then().log().all();
        statusCode=response.statusCode();
        errorMessage=response.body().jsonPath().getString("error");
    }

    @Given("user finds non existing department id")
    public void user_finds_non_existing_department_id() {
        Response response=given().baseUri("http://localhost:3000/api")
                .when().get("/departments");
        response.then().log().all();

        List<Integer> departmentIds=response.body().jsonPath().getList("department_id", Integer.class);
        nonExistingDepartmentId = Collections.max(departmentIds) + 1;
    }
    @When("user deletes non existing department id")
    public void user_deletes_non_existing_department_id() {
        Response response=given().baseUri("http://localhost:3000/api")
                .and().log().all() // REQUEST details logs/prints
                .when().delete("/departments/"+nonExistingDepartmentId);
        response.then().log().all(); // RESPONSE details logs/prints
        statusCode=response.statusCode();
        errorMessage=response.body().jsonPath().getString("error");
    }

    @Given("user checks if departments exist in database otherwise creates departments")
    public void user_checks_if_departments_exist_in_database_otherwise_creates_departments() throws SQLException {
        JDBCUtils.connectToDatabase(ConfigReader.getProperty("DBURL"),
                ConfigReader.getProperty("DBUsername"),
                ConfigReader.getProperty("DBPassword"));
        List<Map<String,Object>> dbData=JDBCUtils.executeQuery("select * from departments");

        if(dbData.size()<10){
            for(int i=1; i<=10; i++){
                Response response = given().baseUri("http://localhost:3000/api")
                        .and().header("Content-Type", "application/json")
                        .and().body("{\n" +
                                "  \"name\": \"Enrollment\",\n" +
                                "  \"location_id\": \"2400\"\n" +
                                "}")
                        .when().post("/departments");
                response.then().log().all();
            }
        }

    }
    @When("user gets departments with get api call with {int} limit")
    public void user_gets_departments_with_get_api_call_with_limit(Integer limit) {
        Response response=given().baseUri("http://localhost:3000/api")
                .and().queryParam("limit",limit)
                .and().log().all()
                .when().get("/departments");
        response.then().log().all();

        numberOfDepartmentsInResponse = response.body().jsonPath().getList("department_id").size();
    }
    @Then("user validates {int} departments in response")
    public void user_validates_departments_in_response(int expectedNumberOfDepartments) {
        Assert.assertEquals( expectedNumberOfDepartments, numberOfDepartmentsInResponse );
    }

    @When("user gets departments with get api call with {string} order")
    public void user_gets_departments_with_get_api_call_with_order(String order) {
        Response response=given().baseUri("http://localhost:3000/api")
                .and().queryParam("order",order)
                .and().log().all()
                .when().get("/departments");
        response.then().log().all();

        departmentNames = response.body().jsonPath().getList("department_name");
    }

    @Then("user validates departments are in {string} order in response")
    public void user_validates_departments_are_in_order_in_response(String expectedOrder) {
        List<String> expectedDepartmentNames=new ArrayList<>();
        for(String department: departmentNames){
            expectedDepartmentNames.add(department);
        }
        if(expectedOrder.equals("asc")){
            Collections.sort(expectedDepartmentNames);
        }else if(expectedOrder.equals("desc")){
            Collections.sort(expectedDepartmentNames, Collections.reverseOrder());
        }
        Assert.assertTrue(departmentNames.equals(expectedDepartmentNames));
    }

}















