import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTest {

    private static Response response;
    @Before
    public void setUp() {
        response = Steps.createCourier("spartak", "123456", "Антон");
        response.then().assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Вход с некорректным логином")
    @Description("Попытка входа с логином, которого нет в системе. Статус ответа 404. Сообщение: Учетная запись не найдена")
    public void incorrectLoginTest() {
         response = Steps.loginCourier("lokolok","123456");
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
        response = Steps.loginCourier("spartak","654321");
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
        response = Steps.loginCourier("", "123456");
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
        response = Steps.loginCourier("spartak", "");
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
        response = Steps.loginCourier("spartak", "123456");
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", Matchers.notNullValue());
    }

    @After
    public void deleteCourier() {
        int id = Steps.loginCourier("spartak", "123456").then().extract().path("id");
        response = Steps.deleteCourier(id);
    }
}
