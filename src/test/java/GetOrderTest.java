import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class GetOrderTest {
    private final Order order = new Order();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Список заказов. Код ответа 200.")
    public void getOrderTest() {
        Response response = order.getOrder();
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("orders", hasSize(greaterThan(0)));
    }
}
