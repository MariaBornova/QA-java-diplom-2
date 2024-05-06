package constants;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Data {
    protected static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";
    public static final String INGREDIENTS = "api/ingredients";
    public static final String ORDERS = "api/orders";
    public static final String USER_REGISTER = "api/auth/register";
    public  static final String USER_LOGIN = "api/auth/login";
    public static final String USER = "api/auth/user";

    public static RequestSpecification getSpec() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }
}
