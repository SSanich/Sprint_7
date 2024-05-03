package courier;

import org.axample.Constants;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CourierTest {
    int id;

    @Test
    public void courier() {
        var courier = Courier.generic();
        boolean created = given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URI_SCOOTER_STRING)
                .body(courier)
                .when()
                .post(Constants.COURIER_PATH_STRING)
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("ok");


        var creds = CourierCredentials.from(courier);
        id = given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URI_SCOOTER_STRING)
                .body(creds)
                .when()
                .post(Constants.COURIER_PATH_STRING + "login")
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id");

        assertTrue(created);
        assertNotEquals(0, id);

    }

    @Test
    public void courierDel() {
        String json = "{\"login\": \"Sanch\", \"password\": \"1234\"}";
        given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(Constants.BASE_URI_SCOOTER_STRING)
                .body(json)
                .when()
                .delete("/api/v1/courier/" + id)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");

    }
}