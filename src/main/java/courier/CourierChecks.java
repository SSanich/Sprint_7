package courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CourierChecks {
    @Step("Check code 200 courier logged successfully")

    public int loggedInSuccessfully(ValidatableResponse loginResponse) {
        return loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id");
    }

    @Step("Check code 201 courier created successfully")

    public void createdSuccesfully(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("ok");
        assertTrue(created);
    }

    @Step("Check code 200 courier deleted successfully")
    public void deletedSuccesfully(ValidatableResponse deleteResponse) {
        deleteResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("ok",equalTo(true));
    }
    @Step("Check code 409 and message - Этот логин уже используется. Попробуйте другой.")
    public void checkSameCourier(ValidatableResponse sameCourier) {
        sameCourier
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
    @Step("Check code 400 and message - Недостаточно данных для создания учетной записи.")
    public void checkCreateCourierWithoutRequiredField(ValidatableResponse withoutFieldResponse) {
        withoutFieldResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
