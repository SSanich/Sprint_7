package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CourierTest {
    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    int courierId;

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse deleteResponse = client.courierDelete(courierId);
            check.deletedSuccesfully(deleteResponse);
        }
    }

    @DisplayName("Courier create and login")
    @Test
    public void courierTest() {
        //создание курьера
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        //проверка курьер создан
        check.createdSuccesfully(createResponse);
        // Логин курьера
        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        // проверка успешного логина
        courierId = check.loggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);
    }

//    @Test
//    public void courierDelete() {
//        String json = "{\"login\": \"Sanch\", \"password\": \"1234\"}";
//        given().log().all()
//                .contentType(ContentType.JSON)
//                .baseUri(Constants.BASE_URI_SCOOTER_STRING)
//                .when()
//                .delete("/api/v1/courier/"+ courierId)
//                .then().log().all()
//                .assertThat()
//                .statusCode(200)
//                .extract()
//                .path("ok");
//
//    }
//
//    @Test
//    public void getCourierOrderList() {
//        given().log().all()
//                .contentType(ContentType.JSON)
//                .baseUri(Constants.BASE_URI_SCOOTER_STRING)
//                .when()
//                .get("/api/v1/courier/298601/ordersCount")
//                .then().log().all()
//                .assertThat()
//                .statusCode(200);
//                .extract()
//                .path("ok");

//    }
}