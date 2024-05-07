package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
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
    // как разнести по разным классам
    @DisplayName("Courier create and login")
    @Test
    public void courierTest() {
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
    @DisplayName("Check that impossible create same courier")
    @Test
    public void sameCourierTest(){
        var courier = Courier.random();
        client.createCourier(courier); //  создается курьер
        ValidatableResponse sameCourier = client.createCourier(courier);// попытка создать курьера с таким же логином и паролем
        check.checkSameCourier(sameCourier);// проверка ответа
    }
}