import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final Order order = new Order();
    private int track;
    private final String[] colours;

    public CreateOrderTest(String[] colours) {
        this.colours = colours;
    }

    @Parameterized.Parameters
    public static Object[][] getColours() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"RED"}},
                {new String[]{"BLACK", "RED"}},
                {null}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Тест создания заказа с указанием различных вариантов по цветам, в том числе без указания цвета. Ожидаем ответ сервера 201.")
    public void createOrderTest() {
        Response response = order.createOrder(colours);
        response.then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", Matchers.notNullValue());
        this.track = response.then().extract().path("track");
    }

    @After
    public void cancelOrder() {
        Response response = order.cancelOrder(this.track);
    }
}
