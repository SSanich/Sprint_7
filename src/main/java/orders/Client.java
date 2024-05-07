package orders;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import envir.Constants;

import static io.restassured.RestAssured.given;

public class Client {
    public static RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URI_STRING)
                .basePath(Constants.BASE_PATH_STRING)
                ;
    }
}
