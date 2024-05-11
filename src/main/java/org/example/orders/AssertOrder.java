package org.example.orders;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsNot.not;

public class AssertOrder {
    @Step("Успешное получение списка заказов")
    public void getOrderListSuccessfully(ValidatableResponse response) {
        response.log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("body.id",  not(empty()));
    }
}