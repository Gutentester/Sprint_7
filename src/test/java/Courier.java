import constants.Constants;
import io.restassured.response.Response;
import pojos.CourierPOJO;

import static io.restassured.RestAssured.*;
public class Courier {
    public Response createCourier(String login, String password, String firstName) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(new CourierPOJO(login, password, firstName))
                .post(Constants.BASE_URI + Constants.CREATE_COURIER);
        return response;
    }

    public Response loginCourier(String login, String password) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(new CourierPOJO(login, password))
                .post(Constants.BASE_URI + Constants.LOGIN_COURIER);
        return response;
    }

    public Response deleteCourier(int id) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(new CourierPOJO(id))
                .delete(Constants.BASE_URI + Constants.DELETE_COURIER + id);
        return response;
    }
}
