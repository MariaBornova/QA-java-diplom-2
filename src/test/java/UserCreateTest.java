import user.User;
import user.UserApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserCreateTest {
    String accessToken;

    @Test
    @DisplayName("New user can be created")
    public void testCanCreateNewUser() {
        User user = UserApi.createNewUser();
        Response response = UserApi.create(user);
        Assert.assertEquals("Неправильный статус код", SC_OK, response.statusCode());
    }


    @Test
    @DisplayName("Can not create user without email")
    public void testCanNotCreateUserWithoutEmail() {
        User user = UserApi.createNewUser();
        user.setEmail(null);
        Response response = UserApi.create(user);
        response.then().assertThat().statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }


    @Test
    @DisplayName("Can not register a user which is already created")
    public void checkDoubleUserCreate() {
        User user = UserApi.createNewUser();
        Response response = UserApi.create(user);
        accessToken = response.path("accessToken");
        Assert.assertEquals("Неправильный статус код", SC_OK, response.statusCode());
        Response responseDoubleUser = UserApi.create(user);
        responseDoubleUser.then().assertThat().statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("User already exists"));
    }

    @After
    public void downUp() {
        if (accessToken != null) UserApi.delete(accessToken);
    }
}

