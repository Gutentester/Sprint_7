import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {

    private String login;
    private String password;
    private String name;

    Faker faker = new Faker();

    @Before
    public void setUp() {
        login = faker.name().username();
        password = faker.internet().password();
        name = faker.name().name();
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Корректное создание курьера. Статус ответа 201.")
    public void correctCreateCourierTest() {
        Response response = Steps.createCourier(login, password, name);
        response.then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Попытка создания курьера без пароля")
    @Description("Пытаемся создать курьера без указания пароля. Сатуст ответа 400. " +
                "Сообщение: Недостаточно данных для создания учетной записи")
    public void withoutPasswordCreateCourierTest() {
        Response response = Steps.createCourier(login, "", name);
        response.then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Попытка повторного использования логина")
    @Description("Пытаемся создать курьера с логином, который уже используется. Сатуст ответа 409. " +
                "Сообщение: Этот логин уже используется")
    public void usedPasswordCreateCourierTest() {
        Response response = Steps.createCourier(login, password, name);
        response.then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
        response = Steps.createCourier(login, password, name);
        response.then()
                .assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void deleteCourier() {
            Response response = Steps.loginCourier(login, password);
            int id;
            try {
                id = response.then().extract().path("id");
            } catch (NullPointerException exception) {
                return;
            }
            response = Steps.deleteCourier(id);
    }
}
