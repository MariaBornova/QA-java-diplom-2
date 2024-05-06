import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import user.User;
import user.UserApi;
public class UserLoginTest {

    String accessToken;


    @Test
    @DisplayName("An existing user can be logged in")
    public void testCanLoginExistingUser() {
        User user = UserApi.createNewUser();
        Response responseLogin = UserApi.create(user);
        responseLogin.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }


    @Test
    @DisplayName("User can not login with wrong Login(in this app email is Login)")
    public void testCanNotLoginWithWrongLogin() {
        String newEmail = RandomStringUtils.randomAlphabetic(10) + "@mail.rutest";
        User user = UserApi.createNewUser();
        Response responseLogin = UserApi.create(user);
        accessToken = responseLogin.path("accessToken");
        user.setEmail(newEmail);
        Response responseLogin2 = UserApi.login(user, accessToken);
        responseLogin2.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("email or password are incorrect"));
        user.setEmail(user.getEmail());

    }

    @After
    public void downUp() {
        if (accessToken != null) UserApi.delete(accessToken);
    }
}
