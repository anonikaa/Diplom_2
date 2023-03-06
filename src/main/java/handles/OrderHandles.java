package handles;

import io.restassured.response.ValidatableResponse;
import pojo.Ingredients;

import static io.restassured.RestAssured.given;

public class OrderHandles {
    public ValidatableResponse createOrder(Ingredients ingredients, String token){
        return given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .body(ingredients)
                .when()
                .post("/api/orders").then();
    }
    public ValidatableResponse getUserOrder(String token){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .get("/api/orders").then();
    }
}
