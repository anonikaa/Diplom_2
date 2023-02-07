import handles.RegisterHandles;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.Register;

import static org.junit.Assert.assertEquals;

public class RegisterTest {
    RegisterHandles registerHandles = new RegisterHandles();

    //переписать чтобы заработал
    @Test
    public void successRegister(){
        Register register = new Register("login", "password", "name");

        ValidatableResponse response = registerHandles.registerNewUser(register);
        assertEquals(200, response.extract().statusCode());
    }
    @Test
    public void registerExistedUser(){
        Register register = new Register("ajaj@gmail.com", "123qwerty", "Name");

        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("User already exists", message);
    }
    @Test
    public void registerWithEmptyEmail(){
        Register register = new Register("", "password", "name");

        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }

    @Test
    public void registerWithEmptyPassword(){
        Register register = new Register("ajaj@gmail.com", "", "name");

        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }
    @Test
    public void registerWithEmptyName(){
        Register register = new Register("ajaj@gmail.com", "111", "");

        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }

}
