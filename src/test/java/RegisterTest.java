import handles.RegisterHandles;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Register;

import static org.junit.Assert.assertEquals;

public class RegisterTest {
    private RegisterHandles registerHandles;
    private Register register;
    private String token;


    @Test
    public void successRegister(){
        register = new Register("anana@hrr.com", "123456", "name");
        registerHandles = new RegisterHandles();
        ValidatableResponse response = registerHandles.registerNewUser(register);
        assertEquals(200, response.extract().statusCode());
        token = response.extract().path("accessToken");
        registerHandles.deleteUser(token);
    }
    @Test
    public void registerExistedUser(){
        register = new Register("test@test.com", "123456", "name");
        registerHandles = new RegisterHandles();
        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("User already exists", message);
    }
    @Test
    public void registerWithEmptyEmail(){
        register = new Register("", "123456", "name");
        registerHandles = new RegisterHandles();
        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }

    @Test
    public void registerWithEmptyPassword(){
        register = new Register("anana@hrr.com", "", "name");
        registerHandles = new RegisterHandles();
        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }
    @Test
    public void registerWithEmptyName(){
        register = new Register("anana@hrr.com", "123456", "");
        registerHandles = new RegisterHandles();
        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }
}
