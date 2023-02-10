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
                .post("/api/auth/register").then();
    }
    public ValidatableResponse deleteUser (String token){
        return given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .when()
                .delete("/api/auth/user").then();
    }
}
