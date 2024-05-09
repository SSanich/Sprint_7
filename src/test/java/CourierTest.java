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
    @DisplayName("Courier create with required fields")
    @Description("Courier created test. POST/api/v1/org.example.courier")
    @Test
    public void courierCreate() {
        //создание курьера
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        //проверка курьер создан
        check.createdSuccessfully(createResponse);
        // Логин курьера
        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        // проверка успешного логина
        courierId = check.loggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);
    }
    @Test
    @DisplayName("Create  without login. Negative")
    @Description("Courier creation without login. Negative test. POST/api/v1/org.example.courier")
    public void courierCreationWithoutLogin() {
        Courier courier = new Courier("", "1234", "Lion");
        ValidatableResponse response = client.createCourier(courier);
        check.checkCreateCourierWithoutRequiredField(response);
    }
    @Test
    @DisplayName("Create courier without password. Negative")
    @Description("Courier creation without password. Negative test. POST/api/v1/org.example.courier")
    public void courierCreationWithoutPassword() {
        var courier = Courier.random();
        courier.setPassword("");
        ValidatableResponse response = client.createCourier(courier);
        check.checkCreateCourierWithoutRequiredField(response);
    }
    //  тест проварливается, согласно апидок, поле firstName обязательное
    @Test
    @DisplayName("Create courier without firstName.Negative")
    @Description("Courier creation without firstName. Negative test. POST/api/v1/org.example.courier")
    public void courierCreationWithoutFirstName() {
        var courier = Courier.random();
        courier.setFirstName("");
        ValidatableResponse response = client.createCourier(courier);
        check.checkCreateCourierWithoutRequiredField(response);
    }

//  тест падает, так как возвращается не ожидаемый ответ
    @DisplayName("Check that impossible create same courier.Negative")
    @Description("Can not create two identical couriers. Negative test. POST/api/v1/org.example.courier")
    @Test
    public void sameCourierTest(){
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        //проверка курьер создан
        check.createdSuccessfully(createResponse);
        // Логин курьера
        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        // проверка успешного логина
        courierId = check.loggedInSuccessfully(loginResponse);
        ValidatableResponse sameCourier = client.createCourier(courier);// попытка создать курьера с таким же логином и паролем
        // проверка ответа
        check.checkSameCourier(sameCourier);
    }
}