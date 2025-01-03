package APITESTING;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class ApiTesting1 {
    @Test
    public void testGetAllUsers() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0)); // Ensure the response has users
    }


    @Test
    public void testCreateUser() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Request payload
        String requestBody = """
                {
                    "name": "John Doe",
                    "username": "johndoe",
                    "email": "johndoe@example.com"
                }
                """;

        // Perform POST request and validate response
        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("John Doe"))
                .body("email", equalTo("johndoe@example.com"));
    }
    @Test
    public void testGetUserById() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Perform GET request for a specific user and validate response
        given()
                .pathParam("id", 1)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", notNullValue());
    }

    @Test
    public void testUpdateUser() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Request payload
        String requestBody = """
        {
            "name": "Jane Doe",
            "username": "janedoe"
        }
        """;

        // Perform PUT request and validate response
        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .pathParam("id", 1)
                .when()
                .put("/users/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Jane Doe"))
                .body("username", equalTo("janedoe"));
    }

    @Test
    public void testDeleteUser() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Perform DELETE request and validate response
        given()
                .pathParam("id", 1)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(200);
    }
}