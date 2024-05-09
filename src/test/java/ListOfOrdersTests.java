import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.orders.AssertOrder;
import org.example.orders.OrderClient;
import org.junit.Before;
import org.junit.Test;

public class ListOfOrdersTests {
    private OrderClient orderClient;
    private AssertOrder assertOrder = new AssertOrder();

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("check whether a list of orders is received in the response body")
    @Description("List of orders test. GET/api/v1/orders")
    public void ListOfOrders() {
        ValidatableResponse response = orderClient.getOrderList();
        assertOrder.getOrderListSuccessfully(response);
    }
}