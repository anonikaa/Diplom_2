import handles.RegisterHandles;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.Register;

import static org.junit.Assert.assertEquals;

public class RegisterTest {
    private RegisterHandles registerHandles;
    private Register register;
    private String token;


    @Test
    public void successRegister(){
        register = new Register(TestData.registerEmail, TestData.registerPassword, TestData.registerName);
        registerHandles = new RegisterHandles();
        ValidatableResponse response = registerHandles.registerNewUser(register);
        assertEquals(200, response.extract().statusCode());
        token = response.extract().path("accessToken");
        registerHandles.deleteUser(token);
    }
    @Test
    public void registerExistedUser(){
        register = new Register(TestData.EXIST_EMAIL, TestData.EXIST_PASSWORD, TestData.EXIST_NAME);
        registerHandles = new RegisterHandles();
        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("User already exists", message);
    }
    @Test
    public void registerWithEmptyEmail(){
        register = new Register(TestData.EMPTY_FIELD, TestData.registerPassword, TestData.registerName);
        registerHandles = new RegisterHandles();
        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }

    @Test
    public void registerWithEmptyPassword(){
        register = new Register(TestData.registerEmail, TestData.EMPTY_FIELD, TestData.registerName);
        registerHandles = new RegisterHandles();
        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }
    @Test
    public void registerWithEmptyName(){
        register = new Register(TestData.registerEmail, TestData.registerPassword, TestData.EMPTY_FIELD);
        registerHandles = new RegisterHandles();
        ValidatableResponse response = registerHandles.registerNewUser(register);
        String message = response.extract().path("message");
        assertEquals(403, response.extract().statusCode());
        assertEquals("Email, password and name are required fields", message);
    }
}
