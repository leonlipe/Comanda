package redleon.net.comanda.model;

/**
 * Created by leon on 09/07/15.
 */
public class JsonOrderDishesResult {

    private String status;
    private OrderDishesData[] order_dishes_data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderDishesData[] getOrder_dishes_data() {
        return order_dishes_data;
    }

    public void setOrder_dishes_data(OrderDishesData[] order_dishes_data) {
        this.order_dishes_data = order_dishes_data;
    }
}
