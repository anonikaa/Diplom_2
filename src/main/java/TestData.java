import com.github.javafaker.Faker;
public class TestData {
    public static Faker faker = new Faker();
    public static String registerEmail = faker.internet().emailAddress();
    public static String registerPassword = faker.internet().password();
    public static String registerName = faker.name().name();
    public static final String EMPTY_FIELD = "";
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

}
