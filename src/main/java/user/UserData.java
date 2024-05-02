package user;
import org.apache.commons.lang3.RandomStringUtils;


public class UserData {
    public static User CreateNewUser() {
        return new User(RandomStringUtils.randomAlphabetic(10) + "@mail.rutest",
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10)); // 6 символов для позитивного теста
    }
}
