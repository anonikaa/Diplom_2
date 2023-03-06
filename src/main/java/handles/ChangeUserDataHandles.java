package handles;

import io.restassured.response.ValidatableResponse;
import pojo.ChangeUserData;

import static io.restassured.RestAssured.given;

public class ChangeUserDataHandles {
    public ValidatableResponse changeUserData (ChangeUserData changeUserData, String token){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(changeUserData)
                .when()
                .patch("/api/auth/user").then();
    }
}
