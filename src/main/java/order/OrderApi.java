package order;

import static constants.Data.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderApi {

    @Step("Create order with token")
    public static Response createOrderWithAuthorized(Order order, String accessToken) {
        return getSpec()
                .headers("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDERS);
    }

    @Step("Get ingredient's data")
    public static Ingredients getIngredient() {
        return getSpec()
                .when()
                .get(INGREDIENTS)
                .body()
                .as(Ingredients.class);
    }


    @Step("Create order without token")
    public static Response createOrderWithoutAuthorized(Order order) {
        return getSpec()
                .body(order)
                .when()
                .post(ORDERS);
    }

    @Step("Get orders user with token")
    public static Response getUserOrders(String accessToken) {
        return getSpec()
                .headers("Authorization", accessToken)
                .when()
                .get(ORDERS);
    }
    @Step("Get orders user without token")
    public static Response getUserOrderWithoutToken() {
        return getSpec()
                .when()
                .get(ORDERS);
    }
}
