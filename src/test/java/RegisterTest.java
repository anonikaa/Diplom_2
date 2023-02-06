import handles.RegisterHandles;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.Register;

import static org.junit.Assert.assertEquals;

public class RegisterTest {
    RegisterHandles registerHandles = new RegisterHandles();
    @Test
    public void successRegister(){
        Register register = new Register("login", "password", "name");

        ValidatableResponse response = registerHandles.registerNewUser(register);
        assertEquals(200, response.extract().statusCode());
    }
}
