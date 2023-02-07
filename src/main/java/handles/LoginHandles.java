package handles;
import io.restassured.response.ValidatableResponse;
import pojo.Login;

import static io.restassured.RestAssured.given;

public class LoginHandles {
    public ValidatableResponse login (Login login){
        return given()
                .header("Content-type", "application/json")
                .body(login)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/auth/login").then();
    }

}
