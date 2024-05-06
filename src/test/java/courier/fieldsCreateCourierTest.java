package courier;

import org.junit.runners.Parameterized;

public class fieldsCreateCourierTest {
    private final String login;
    private final String password;

    public fieldsCreateCourierTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"CourierLogin", null},
                {null, "LoginPass"},
        };
    }


}
