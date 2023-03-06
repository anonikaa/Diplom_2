import handles.OrderHandles;
import handles.RegisterHandles;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.Ingredients;
import pojo.Register;

public class CreateOrderTest {
    private Register register;
    private RegisterHandles registerHandles;
    private OrderHandles orderHandles;
    String id = "61c0c5a71d1f82001bdaaa6d";
    String token;
    @Before
    public void setUp(){
        RestAssured.baseURI = TestData.BASE_URL;
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
    public void successCreateOrder(){
        Ingredients ingredients = new Ingredients(id);
        ValidatableResponse response = orderHandles.createOrder(ingredients, token);
        Boolean success = response.extract().path("success");
        Assert.assertEquals(200, response.extract().statusCode());
        Assert.assertEquals(true, success);

    }
    @Test
    //тут баг, но может и фича. Но я не понимаю почему можно создать заказ без авторизации, кому он создатся
    public void createOrderNoAuthorization(){
        Ingredients ingredients = new Ingredients(id);
        ValidatableResponse response = orderHandles.createOrder(ingredients, "");
        Boolean success = response.extract().path("success");
        Assert.assertEquals(400, response.extract().statusCode());
        Assert.assertEquals(false, success);
    }
    @Test
    public void createOrderWithoutIngredients(){
        Ingredients ingredients = new Ingredients("");
        ValidatableResponse response = orderHandles.createOrder(ingredients, token);
        String message = response.extract().path("message");
        Assert.assertEquals(400, response.extract().statusCode());
        Assert.assertEquals("Ingredient ids must be provided", message);
    }
    @Test
    public void createOrderWrongHashOfIngredients(){
        Ingredients ingredients = new Ingredients("wrong id");
        ValidatableResponse response = orderHandles.createOrder(ingredients, token);
        Assert.assertEquals(500, response.extract().statusCode());
    }
}
