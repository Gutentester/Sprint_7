import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTest {

    Faker faker = new Faker();
    private String login = faker.name().username();
    private String password = faker.internet().password();
    private String name = faker.name().name();
    private static Response response;

    @Before
    public void setUp() {
        response = Steps.createCourier(login, password, name);
        response.then().assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Вход с некорректным логином")
    @Description("Попытка входа с логином, которого нет в системе. Статус ответа 404. Сообщение: Учетная запись не найдена")
    public void incorrectLoginTest() {
         response = Steps.loginCourier(login + "123",password);
         response.then()
                 .assertThat()
                 .statusCode(404)
                 .and()
                 .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Вход с некорректным логином")
    @Description("Попытка входа с логином, которого нет в системе. Статус ответа 404. Сообщение: Учетная запись не найдена")
    public void incorrectPasswordTest() {
        response = Steps.loginCourier(login,password + "123");
        response.then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Вход без логина")
    @Description("Попытка входа с пустым логином. Статус ответа 400. Сообщение: Недостаточно данных для входа")
    public void missingLoginTest() {
        response = Steps.loginCourier("", password);
        response.then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Вход без пароля")
    @Description("Попытка входа с пустым паролем. Статус ответа 400. Сообщение: Недостаточно данных для входа")
    public void missingPasswordTest() {
        response = Steps.loginCourier(login, "");
        response.then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Вход с корректной учетной записью")
    @Description("Вход с учетной записью, которая есть в системе. Статус ответа 200. Сообщение: Недостаточно данных для входа")
    public void correctLoginCourierTest() {
        response = Steps.loginCourier(login, password);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", Matchers.notNullValue());
    }

    @After
    public void deleteCourier() {
        int id = Steps.loginCourier(login, password).then().extract().path("id");
        response = Steps.deleteCourier(id);
    }
}
