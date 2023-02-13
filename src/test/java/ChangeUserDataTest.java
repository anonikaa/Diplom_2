import handles.ChangeUserDataHandles;
import handles.OrderHandles;
import handles.RegisterHandles;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.ChangeUserData;
import pojo.Register;

public class ChangeUserDataTest {
    Register register;
    RegisterHandles registerHandles;
    ChangeUserDataHandles changeUserDataHandles;
    String token;
    ChangeUserData changeUserData;
    @Before
    public void setUp(){
        RestAssured.baseURI = TestData.BASE_URL;
        register = new Register(TestData.registerEmail, TestData.registerPassword, TestData.registerName);
        registerHandles = new RegisterHandles();
        token = registerHandles.registerNewUser(register).extract().path("accessToken");
        changeUserDataHandles = new ChangeUserDataHandles();
    }
    @After
    public void cleanUp(){
        registerHandles.deleteUser(token);
    }

    @Test
    public void changeEmailWithAuthorization(){
        changeUserData = new ChangeUserData("newemail@test.com", register.getEmail());
        ValidatableResponse response = changeUserDataHandles.changeUserData(changeUserData, token);
        String email = response.extract().path("user.email");
        Assert.assertEquals(200, response.extract().statusCode());
        Assert.assertEquals("newemail@test.com", email);
    }
    @Test
    public void changeNameWithAuthorization(){
        changeUserData = new ChangeUserData(register.getName(), "newName");
        ValidatableResponse response = changeUserDataHandles.changeUserData(changeUserData, token);
        String name = response.extract().path("user.name");
        Assert.assertEquals(200, response.extract().statusCode());
        Assert.assertEquals("newName", name);
    }
    @Test
    public void changeEmailNoAuthorization(){
        changeUserData = new ChangeUserData(register.getEmail(), "newEmail@test.com");
        ValidatableResponse response = changeUserDataHandles.changeUserData(changeUserData, "");
        String message = response.extract().path("message");
        Assert.assertEquals(401, response.extract().statusCode());
        Assert.assertEquals("You should be authorised", message);
    }
    @Test
    public void changeNameNoAuthorization(){
        changeUserData = new ChangeUserData(register.getName(), "newName");
        ValidatableResponse response = changeUserDataHandles.changeUserData(changeUserData, "");
        String message = response.extract().path("message");
        Assert.assertEquals(401, response.extract().statusCode());
        Assert.assertEquals("You should be authorised", message);
    }
}
