import order.Ingredients;
import user.User;
import user.UserApi;
import order.Order;
import order.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;


import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;



public class OrderCreateTest {

    private String accessToken;

    @Before
    public void setUp() {
        User user = UserApi.createNewUser();
        Response response = UserApi.create(user);
        accessToken = response.path("accessToken");
    }

    @Test
    @DisplayName("Create order with token")
    public void testOrderCreateWithAuth() {
        Ingredients ingredients = OrderApi.getIngredient();
        ArrayList<String> ingredient1 = new ArrayList<>();
        ingredient1.add(ingredients.getData().get(1).get_id());
        ingredient1.add(ingredients.getData().get(2).get_id());
        ingredient1.add(ingredients.getData().get(3).get_id());
        Order order = new Order(ingredient1);
        OrderApi.createOrderWithAuthorized(order, accessToken).then().assertThat().body("success", equalTo(true)).and().statusCode(SC_OK);
    }

    @Test
    @DisplayName("Create order without token")
    public void testOrderCreateWithoutAuth() {
        Ingredients ingredients = OrderApi.getIngredient();
        ArrayList<String> ingredient1 = new ArrayList<>();
        ingredient1.add(ingredients.getData().get(1).get_id());
        ingredient1.add(ingredients.getData().get(2).get_id());
        ingredient1.add(ingredients.getData().get(3).get_id());
        Order order = new Order(ingredient1);
        OrderApi.createOrderWithoutAuthorized(order).then().assertThat().body("success", equalTo(true)).and().statusCode(SC_OK);
    }

    @After
    public void downUp() {
        if (accessToken != null) UserApi.delete(accessToken);
    }
}

