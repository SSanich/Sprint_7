package login;

import courier.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

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
        //передаем неверный логин
        var mainLogin = creds.getLogin();
        var mainPassword = creds.getPassword();
        creds.setLogin("WrongLogin");
        ValidatableResponse wrongLoginResponse = client.loginCourier(creds);
        loginCheck.wrongFieldValueCheck(wrongLoginResponse);
        // передаем неверный пароль
        creds.setLogin(mainLogin);
        creds.setPassword("WrongPassword");
        ValidatableResponse wrongPasswordResponse = client.loginCourier(creds);
        loginCheck.wrongFieldValueCheck(wrongPasswordResponse);
        // передаем без поля password
//        creds.setPassword(null);
//        ValidatableResponse withoutPasswordResponse= client.loginCourier(creds);
//        loginCheck.withoutRequiredFieldLoginCheck(withoutPasswordResponse);
        // передаем без логина
        creds.setLogin(null);
        creds.setPassword(mainPassword);
        ValidatableResponse withoutLoginResponse= client.loginCourier(creds);
        loginCheck.withoutRequiredFieldLoginCheck(withoutLoginResponse);

    }


}
//    .body().as(Courier.class)
// courier.setPassword("1235");///////
