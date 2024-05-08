package login;

import courier.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotEquals;

public class LoginTest {
    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    private final LoginCheck loginCheck = new LoginCheck();
    int courierId;

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse deleteResponse = client.courierDelete(courierId);
            check.deletedSuccesfully(deleteResponse);
        }
    }

    @DisplayName("Login test")
    @Test
    public void loginTest() {
        // создаем курьера, получаем id
        var courier = Courier.random();
        client.createCourier(courier);
        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = loginResponse.extract().path("id");
        //передаем неверный лонгин
        creds.setLogin("WrongLogin");
        ValidatableResponse wrongLoginResponse = client.loginCourier(creds);
        loginCheck.wrongLoginCheck(wrongLoginResponse);
        // передаем неверный пароль
        creds.setPassword("WrongPassword");
        ValidatableResponse wrongPasswordResponse = client.loginCourier(creds);
        loginCheck.wrongPasswordCheck(wrongPasswordResponse);
        // передаем без поля login
        client.loginCourier(creds);
        var  credsWrongLogin= creds;
        client.loginCourier(credsWrongLogin);
    }


}
//    .body().as(Courier.class)
// courier.setPassword("1235");///////
