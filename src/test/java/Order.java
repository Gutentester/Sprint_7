import constants.Constants;
import io.restassured.response.Response;
import pojos.OrderPOJO;

import static io.restassured.RestAssured.*;

public class Order {
    public Response createOrder(String[] colour) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(new OrderPOJO(colour))
                .post(Constants.BASE_URI + Constants.ORDERS);
        return response;
    }

    public Response getOrder() {
        Response response = given()
                .header("Content-type", "application/json")
                .get(Constants.BASE_URI + Constants.ORDERS);
        return response;
    }

    public Response cancelOrder(int track) {
        Response response = given()
                .header("Content-type", "application/json")
                .put(Constants.BASE_URI + Constants.ORDERS_CANCEL + track);
        return response;
    }
}
