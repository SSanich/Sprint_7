package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import envir.Constants;
import orders.Client;

import java.util.Map;

import static io.restassured.RestAssured.given;

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
    public static ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(Constants.COURIER_PATH_STRING)
                .then().log().all();
    }
@Step ("Delete courier")
    public ValidatableResponse courierDelete(int id) {
        return spec()
                .body(Map.of("id", id))
                .when()
                .delete(Constants.COURIER_PATH_STRING + "/" + id)
                .then().log().all();
    }
@Step ("create courier without required field")
    public ValidatableResponse createCourierWithParam(Courier courier) {
        return spec()
                .log().all()
                .body(courier)
                .when()
                .post(Constants.COURIER_PATH_STRING)
                .then().log().all();
    }
}
