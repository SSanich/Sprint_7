package courier;

import envir.Constants;
import orders.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static orders.Client.spec;
@RunWith(Parameterized.class)
public class FieldsCreateCourierTest {
    private final Courier courier;

    public FieldsCreateCourierTest(Courier courier) {
        this.courier = courier;
    }


    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {new Courier(null, "LoginPass",null)},
                {new Courier("CourierLogin", null,null)}
        };
    }
    @Test
    CourierClient.createCourier();
//    public void createCourierWithoutRequiredField() throws InterruptedException {
//        spec()
//                .log().all()
//                .body(creds)
//                .when()
//                .post(Constants.COURIER_PATH_STRING)
//                .then().log().all();
//    }

}
