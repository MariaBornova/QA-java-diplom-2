import user.User;
import user.UserApi;
import order.Order;
import order.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class OrderGetTest {
    private String accessToken;

    @Before
    public void setUp() {
        User user = UserApi.createNewUser();
        Response response = UserApi.create(user);
        accessToken = response.path("accessToken");
        Order order = new Order(List.of("61c0c5a71d1f82001bdaaa76", "61c0c5a71d1f82001bdaaa6c"));
        OrderApi.createOrderWithAuthorized(order, accessToken).then().assertThat()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Get user's orders with token")
    public void testsOrderGetWithToken() {
        OrderApi.getUserOrders(accessToken).then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Get user's orders without token")
    public void testsOrderGetWithoutToken() {
        OrderApi.getUserOrderWithoutToken().then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void downUp() {
        if (accessToken != null) UserApi.delete(accessToken);
    }
}
