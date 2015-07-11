package redleon.net.comanda.model;

import java.math.BigDecimal;

/**
 * Created by leon on 19/05/15.
 */
public class PaymentsResult {
    private Integer id;
    private Integer diner_number;
    private String diner_desc;
    private OrderDish[] order_dishes;
    private BigDecimal total;
    private String total_desc;
    private String checkbox_name;
    private Integer status;
    private String status_desc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDiner_number() {
        return diner_number;
    }

    public void setDiner_number(Integer diner_number) {
        this.diner_number = diner_number;
    }

    public String getDiner_desc() {
        return diner_desc;
    }

    public void setDiner_desc(String diner_desc) {
        this.diner_desc = diner_desc;
    }

    public OrderDish[] getOrder_dishes() {
        return order_dishes;
    }

    public void setOrder_dishes(OrderDish[] order_dishes) {
        this.order_dishes = order_dishes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getTotal_desc() {
        return total_desc;
    }

    public void setTotal_desc(String total_desc) {
        this.total_desc = total_desc;
    }

    public String getCheckbox_name() {
        return checkbox_name;
    }

    public void setCheckbox_name(String checkbox_name) {
        this.checkbox_name = checkbox_name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }
}
