import handles.RegisterHandles;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Register;

import static org.junit.Assert.assertEquals;

public class RegisterTest {
    private RegisterHandles registerHandles;
    private Register register;
    private Register registerNew;
    private String token;

    @Before
    public void setUp(){
        RestAssured.baseURI = TestData.BASE_URL;
    }
    @After
    public void cleanUp(){
        token = registerHandles.registerNewUser(register).extract().path("accessToken");
        if (token != null){
            registerHandles.deleteUser(token);
        }
    }

    @Test
    public void successRegister(){
        register = new Register(TestData.registerEmail, TestData.registerPassword, TestData.registerName);
        registerHandles = new RegisterHandles();
        ValidatableResponse response = registerHandles.registerNewUser(register);
        assertEquals(200, response.extract().statusCode());
    }
    @Test
    public void registerExistedUser(){
        register = new Register(TestData.registerEmail, TestData.registerPassword, TestData.registerName);
        registerNew = new Register(register.getEmail(), register.getPassword(), register.getName());
        registerHandles = new RegisterHandles();
        registerHandles.registerNewUser(register);
        ValidatableResponse response = registerHandles.registerNewUser(registerNew);
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
