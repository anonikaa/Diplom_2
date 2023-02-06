package handles;
import io.restassured.response.ValidatableResponse;
import pojo.Register;
import static io.restassured.RestAssured.given;

public class RegisterHandles {
    public ValidatableResponse registerNewUser (Register register){
        return given()
                .header("Content-type", "application/json")
                .body(register)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/auth/register").then();

    }
}
