package user;

import static constants.Data.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;


public class UserApi {
    public static User createNewUser() {
        return new User(UserData.createNewUser().getEmail(), UserData.createNewUser().getPassword(), UserData.createNewUser().getName());
    }

    @Step("User create")
    public static Response create(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(USER_REGISTER);
    }

    @Step("User change data")
    public static Response change(User user, String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER);
    }

    @Step("User authorized")
    public static Response login(User user, String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .post(USER_LOGIN);
    }
    @Step("User delete")
    public static void delete(String accessToken) {
        getSpec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER);
    }
}