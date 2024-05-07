package login;

import courier.Courier;
import courier.CourierChecks;
import courier.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class LoginTest {
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
        @Test
        public void loginTest() {
            var courier = Courier.random();
            ValidatableResponse createResponse = client.createCourier(courier);
            check.createdSuccessfully(createResponse);
            courierId = createResponse.extract().path("id");

            client.loginCourier("Login", "login", "password", "password");
        }
    }

