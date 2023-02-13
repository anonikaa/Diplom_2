import handles.LoginHandles;
import handles.RegisterHandles;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Login;
import pojo.Register;

import static org.junit.Assert.assertEquals;

public class LoginTest {
    private Register register;
    private RegisterHandles registerHandles;
    private LoginHandles loginHandles;
    String token;
    @Before
    public void setUp(){
        RestAssured.baseURI = TestData.BASE_URL;
        register = new Register(TestData.registerEmail, TestData.registerPassword, TestData.registerName);
        registerHandles = new RegisterHandles();
        loginHandles = new LoginHandles();
        token = registerHandles.registerNewUser(register).extract().path("accessToken");
    }
    @After
    public void cleanUp(){
        registerHandles.deleteUser(token);
    }
    @Test
    public void successLoginExistUser(){
        Login login = new Login(register.getEmail(), register.getPassword());
        ValidatableResponse response = loginHandles.login(login);

        assertEquals(200, response.extract().statusCode());
        Boolean success = response.extract().path("success");
        assertEquals(true, success);
    }
    @Test
    public void loginWithWrongEmail(){
        Login login = new Login("ppp@ggg.fu", register.getPassword());
        ValidatableResponse response = loginHandles.login(login);

        assertEquals(401, response.extract().statusCode());
        String message = response.extract().path("message");
        assertEquals("email or password are incorrect", message);
    }
    @Test
    public void loginWithWrongPassword(){
        Login login = new Login(register.getEmail(), "fjfgjkfkj");
        ValidatableResponse response = loginHandles.login(login);

        assertEquals(401, response.extract().statusCode());
        String message = response.extract().path("message");
        assertEquals("email or password are incorrect", message);
    }
}
