import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.axample.Constants;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class FirstTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URI_MESTO_STRING;
    }

    @Test
    @DisplayName("Запуск ручки/users/me")
    @Description(" Проверка как что рабтает")
    @Step("First Step")
    public void getMyInfoStatusCode() {
        // метод given() помогает сформировать запрос
        given()
                // указываем протокол и данные авторизации
                .auth().oauth2(Constants.TOKEN_STRING)
                // отправляем GET-запрос с помощью метода get, недостающую часть URL (ручку) передаём в него в качестве параметра
                .get("/api/users/me")
                // проверяем, что статус-код ответа равен 200
                .then().statusCode(200);
    }
}
