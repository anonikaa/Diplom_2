import handles.OrderHandles;
import handles.RegisterHandles;
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
        register = new Register("login@test.ru", "password", "name");
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
    //тут баг
    public void createOrderNoAuthorization(){
        Ingredients ingredients = new Ingredients(id);
        ValidatableResponse response = orderHandles.createOrder(ingredients, "").log().all();
        Boolean success = response.extract().path("success");
        Assert.assertEquals(400, response.extract().statusCode());
        Assert.assertEquals(false, success);
    }
    @Test
    public void createOrderWithoutIngredients(){
        Ingredients ingredients = new Ingredients("");
        ValidatableResponse response = orderHandles.createOrder(ingredients, token).log().all();
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