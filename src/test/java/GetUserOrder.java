import handles.LoginHandles;
import handles.OrderHandles;
import handles.RegisterHandles;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.Register;

public class GetUserOrder {
    Register register;
    RegisterHandles registerHandles;
    OrderHandles orderHandles;
    String token;
    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        register = new Register(TestData.registerEmail, TestData.registerPassword, TestData.registerName);
        registerHandles = new RegisterHandles();
        orderHandles = new OrderHandles();
        token = registerHandles.registerNewUser(register).extract().path("accessToken");
    }
    @After
    public void cleanUp(){
        registerHandles.deleteUser(token);
    }
    @Test
    public void successGetUserOrder(){
        ValidatableResponse response = orderHandles.getUserOrder(token);
        Boolean success = response.extract().path("success");
        Assert.assertEquals(200, response.extract().statusCode());
        Assert.assertEquals(true, success);
    }
    @Test
    public void getUserOrderNoAuthorization(){
        ValidatableResponse response = orderHandles.getUserOrder("");
        String message = response.extract().path("message");
        Assert.assertEquals(401, response.extract().statusCode());
        Assert.assertEquals("You should be authorised", message);
    }
}
