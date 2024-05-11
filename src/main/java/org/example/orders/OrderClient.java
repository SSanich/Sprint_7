package org.example.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;
import java.net.HttpURLConnection;
import org.example.envсonst.Constants;
public class OrderClient extends Client {

    @Step("Create order")
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post(Constants.ORDERS_PATH_STRING)
                .then()
                .statusCode(HttpURLConnection.HTTP_CREATED);
    }

    @Step("Cancel order")
    public ValidatableResponse cancelOrder(int track) {
        return spec()
                .body("{\"track\":" + track + "}")
                .when()
                .put(Constants.ORDERS_PATH_STRING+"/cancel")
                .then()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Get orders list")
    public ValidatableResponse getOrderList() {
        return spec()
                .when()
                .get(Constants.ORDERS_PATH_STRING)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK);
    }
}