import com.github.javafaker.Faker;
public class TestData {
    public static Faker faker = new Faker();
    public static String registerEmail = faker.internet().emailAddress();
    public static String registerPassword = faker.internet().password();
    public static String registerName = faker.name().name();
    public static final String EXIST_EMAIL = "test@test.com";
    public static final String EXIST_PASSWORD = "123456";
    public static final String EXIST_NAME = "name";
    public static final String EMPTY_FIELD = "";

}
