package utilities;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestAssuredTest {

    public static void main(String[] args) {

        Response response = given().baseUri("http://localhost:3000/api")
                .and().header("Accept","application/json")
                .when().get("/departments/1");

        System.out.println(response.prettyPrint());
        System.out.println(response.statusCode());
        System.out.println(response.getBody().jsonPath().getString("department_name"));

        Response postResponse = given().baseUri("http://localhost:3000/api")
                .and().header("Content-Type","application/json")
                .and().body("{\n" +
                        "  \"name\": \"Enrollment\",\n" +
                        "  \"location_id\": 2400\n" +
                        "}")
                .when().post("/departments");

        System.out.println(postResponse.prettyPrint());
        System.out.println(postResponse.statusCode());

    }

}
