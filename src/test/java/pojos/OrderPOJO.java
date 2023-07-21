package pojos;

public class OrderPOJO {

    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] colour;

    public OrderPOJO() {

    }

    public OrderPOJO(String[] colour) {
        this.colour = colour;
    }

}
