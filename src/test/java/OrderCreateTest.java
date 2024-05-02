import user.User;
import user.UserApi;
import order.Order;
import order.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.util.List;


import static org.apache.http.HttpStatus.*;



@RunWith(Parameterized.class)
public class OrderCreateTest {
    private final List<String> ingredients;
    private final int statusCode;
    private String accessToken;

    public OrderCreateTest(List<String> ingredients, int statusCode) {
        this.ingredients = ingredients;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {List.of("61c0c5a71d1f82001bdaaa76", "61c0c5a71d1f82001bdaaa6c"), SC_OK},
                {List.of("654321", "123456"), SC_INTERNAL_SERVER_ERROR},
                {List.of(), SC_BAD_REQUEST}
        };
    }

    @Before
    public void setUp() {
        User user = UserApi.createNewUser();
        Response response = UserApi.create(user);
        accessToken = response.path("accessToken");
    }

    @Test
    @DisplayName("Create order with token")
    public void testOrderCreateWithAuth() {
        Order order= new Order(ingredients);
        OrderApi.createOrderWithAuthorized(order, accessToken).then().assertThat()
                .statusCode(statusCode);
    }

    @Test
    @DisplayName("Create order without token")
    public void testOrderCreateWithoutAuth() {
        Order order = new Order(ingredients);
        OrderApi.createOrderWithoutAuthorized(order).then().assertThat()
                .statusCode(statusCode);
    }

    @After
    public void downUp() {
        if (accessToken != null) UserApi.delete(accessToken);
    }
}

