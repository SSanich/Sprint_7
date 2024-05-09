import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.courier.Courier;
import org.example.courier.CourierChecks;
import org.example.courier.CourierClient;
import org.example.courier.CourierCredentials;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

public class LoginCourierTest {
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

    @DisplayName("Login courier. Send all required fields")
    @Description("Courier  login successful  test. POST/api/v1/org.example.courier/login")
    @Test
    public void loginTest() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);

        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Invalid login. message :Учетная запись не найдена")
    @Description("Invalid login. Negative test. POST/api/v1/org.example.courier/login")
    public void invalidLogin() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);

        creds.setLogin("InvalidLogin");
        ValidatableResponse invalidloginResponse = client.loginCourier(creds);
        check.invalidFieldValueCheck(invalidloginResponse);
    }

    @Test
    @DisplayName("Invalid login. message :Учетная запись не найдена")
    @Description("Invalid login. Negative test. POST/api/v1/org.example.courier/login")
    public void invalidPassword() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);

        creds.setPassword("InvalidPassword");
        ValidatableResponse invalidPasswordResponse = client.loginCourier(creds);
        check.invalidFieldValueCheck(invalidPasswordResponse);
    }

    @Test
    @DisplayName("Without login. message :Недостаточно данных для входа")
    @Description("Without login. Negative test. POST/api/v1/org.example.courier/login")
    public void withoutLogin() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);

        creds.setLogin("");
        ValidatableResponse withoutLoginResponse = client.loginCourier(creds);
        check.withoutRequiredFieldLoginCheck(withoutLoginResponse);
    }

    @Test
    @DisplayName("Without password. message :Недостаточно данных для входа")
    @Description("Without password. Negative test. POST/api/v1/org.example.courier/login")
    public void withoutPassword() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);

        creds.setPassword("");
        ValidatableResponse withoutPasswordResponse = client.loginCourier(creds);
        check.withoutRequiredFieldLoginCheck(withoutPasswordResponse);
    }
}


