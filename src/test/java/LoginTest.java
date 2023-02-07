import handles.LoginHandles;
import handles.RegisterHandles;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.Login;
import pojo.Register;

import static org.junit.Assert.assertEquals;

public class LoginTest {
    RegisterHandles registerHandles = new RegisterHandles();
    LoginHandles loginHandles = new LoginHandles();
    @Test
    //нужна параметризация данных для реги
    // + саму регу вынести в Before
    public void successLoginExistUser(){
        Register register = new Register("testNasty@test.com", "123", "Nasty");
        registerHandles.registerNewUser(register);

        Login login = new Login(register.getEmail(), register.getPassword());
        ValidatableResponse response = loginHandles.login(login);

        assertEquals(200, response.extract().statusCode());
        Boolean success = response.extract().path("success");
        assertEquals(true, success);
    }
    @Test
    public void loginWithWrongEmail(){
        Register register = new Register("ppp@ppp.tu", "123", "Nasty");
        registerHandles.registerNewUser(register);

        Login login = new Login("ppp@ggg.fu", register.getPassword());
        ValidatableResponse response = loginHandles.login(login);

        assertEquals(401, response.extract().statusCode());
        String message = response.extract().path("message");
        assertEquals("email or password are incorrect", message);
    }
    @Test
    public void loginWithWrongPassword(){
        Register register = new Register("ppp@ppp.tu", "123", "Nasty");
        registerHandles.registerNewUser(register);

        Login login = new Login(register.getEmail(), "fjfgjkfkj");
        ValidatableResponse response = loginHandles.login(login);

        assertEquals(401, response.extract().statusCode());
        String message = response.extract().path("message");
        assertEquals("email or password are incorrect", message);
    }
}
