import io.restassured.response.Response;
import io.qameta.allure.Step;

public class Steps {

    public static Courier courier = new Courier();

    @Step("POST-request to CREATE courier")
    public static Response createCourier(String login, String password, String name) {
        Response response = courier.createCourier(login, password, name);
        return response;
    }

    @Step("POST-request to LOGIN courier")
    public static Response loginCourier(String login, String password) {
        Response response = courier.loginCourier(login, password);
        return response;
    }

    @Step("DELETE-request to DELETE courier")
    public static Response deleteCourier(int id) {
        Response response = courier.deleteCourier(id);
        return response;
    }
}
