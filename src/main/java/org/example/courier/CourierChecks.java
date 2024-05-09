package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CourierChecks {
    @Step("Check code 200 org.example.courier logged successfully")

    public int loggedInSuccessfully(ValidatableResponse loginResponse) {
        return loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id");
    }

    @Step("Check code 201 org.example.courier created successfully")

    public void createdSuccessfully(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("ok");
        assertTrue(created);
    }

    @Step("Check code 200 org.example.courier deleted successfully")
    public void deletedSuccesfully(ValidatableResponse deleteResponse) {
        deleteResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("ok",equalTo(true));
    }
    @Step("Check code 409 and message - Этот логин уже используется")
    public void checkSameCourier(ValidatableResponse sameCourier) {
        sameCourier
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }
    @Step("Check code 400 and message - Недостаточно данных для создания учетной записи.")
    public void checkCreateCourierWithoutRequiredField(ValidatableResponse withoutFieldResponse) {
        withoutFieldResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Step("Wrong login.Check code 404 and message - Учетная запись не найдена ")
    public static void invalidFieldValueCheck(ValidatableResponse wrongLoginResponse) {
        wrongLoginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Without password.Check code 400 and message - Недостаточно данных для входа ")
    public static void withoutRequiredFieldLoginCheck(ValidatableResponse wrongPasswordResponse) {
        wrongPasswordResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
