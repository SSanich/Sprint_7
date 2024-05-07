package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class FieldsCreateCourierTest {
    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    private final Courier courier;

    public FieldsCreateCourierTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {new Courier(null, "LoginPass", null)},
                {new Courier("CourierLogin", null, null)}
        };
    }
@DisplayName("Create courier without required field")
    @Test
    public void createCourierWithoutRequiredField() {
        ValidatableResponse withoutFieldResponse = client.createCourierWithParam(courier);
        check.checkCreateCourierWithoutRequiredField(withoutFieldResponse);
    }
}
