package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import envir.Constants;
import orders.Client;

import java.net.HttpURLConnection;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierClient extends Client {
    @Step("Login courier")
    public ValidatableResponse loginCourier(CourierCredentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(Constants.COURIER_PATH_STRING + "/login")
                .then().log().all();
    }

    @Step("Create courier")
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(Constants.COURIER_PATH_STRING)
                .then().log().all();
    }

    public ValidatableResponse courierDelete(int id) {
        return spec()
                .body(Map.of("id", id))
                .when()
                .delete(Constants.COURIER_PATH_STRING + "/" + id)
                .then().log().all();
    }

    public ValidatableResponse createSameCourier(Courier courier) {
        return createCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
