package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.envсonst.Constants;
import org.example.Client;
import java.util.Map;

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

}
