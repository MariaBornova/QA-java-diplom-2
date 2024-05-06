import org.apache.commons.lang3.RandomStringUtils;
import user.User;
import user.UserApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserUpdateDataTest {

    String accessToken;

    @Test
    @DisplayName("Authorized user's email can be changed")
    public void testCanChangeAuthorizedUserEmail() {
        String newEmail = RandomStringUtils.randomAlphabetic(10) + "@mail.rutest";
        User user = UserApi.createNewUser();
        Response responseCreate = UserApi.create(user);
        accessToken = responseCreate.path("accessToken");
        user.setEmail(newEmail);
        Response responseUpdate = UserApi.change(user,accessToken);
        responseUpdate.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.email".toLowerCase(), equalTo(user.getEmail().toLowerCase()));
    }


   @Test
   @DisplayName("Unauthorized user's email can not be changed")
    public void testCanNotChangeUnauthorizedUserEmail() {
       String newEmail = RandomStringUtils.randomAlphabetic(10) + "@mail.rutest";
        User user = UserApi.createNewUser();
        accessToken = "";
        user.setEmail(newEmail);
        Response responseUpdate = UserApi.change(user, accessToken);
        responseUpdate.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
        user.setEmail(user.getEmail());
    }

    @After
    public void downUp() {
        if (accessToken != null) UserApi.delete(accessToken);
    }
}
