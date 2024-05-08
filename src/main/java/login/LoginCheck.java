package login;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;


public class LoginCheck {
    @Step("Wrong login.Check code 404 and message - Учетная запись не найдена ")
    public static void wrongLoginCheck(ValidatableResponse wrongLoginResponse) {
        wrongLoginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Step("Wrong password.Check code 404 and message - Учетная запись не найдена ")
    public static void wrongPasswordCheck(ValidatableResponse wrongPasswordResponse) {
        wrongPasswordResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
