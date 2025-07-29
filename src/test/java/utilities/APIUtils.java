package utilities;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;
import pojos.Login;

import static io.restassured.RestAssured.given;

public class APIUtils {

    /*
    .generateToken(); -> generates token and returns it.
     */
    public static String generateToken(String role){
        String username="";
        if(role.equals("HR")) username=ConfigReader.getProperty("HRAPIHrUsername");
        else if(role.equals("HR_Manager")) username=ConfigReader.getProperty("HRAPIAdminUsername");

        Login login=new Login();
        login.setUsername(username);
        login.setPassword(ConfigReader.getProperty("HRAPIPassword"));

        Response response=given().baseUri(ConfigReader.getProperty("HRAPIBaseURL"))
                .and().header("Content-Type","application/json")
                .and().body(login) // Serialization
                .when().post("/login");
        String token=response.getBody().jsonPath().getString("token");
        return token;
    }

}
