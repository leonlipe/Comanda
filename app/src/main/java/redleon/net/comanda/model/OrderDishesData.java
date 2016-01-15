package redleon.net.comanda.model;

/**
 * Created by leon on 26/06/15.
 */
public class OrderDishesData {

    private Integer id;
    private Integer dish_id;
    private Integer diner_id;
    private String dish_name;
    private String dish_desc;
    private String tiime_desc;
    private String status;
    private String place_name;
    private Integer cancel_request;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDish_id() {
        return dish_id;
    }

    public void setDish_id(Integer dish_id) {
        this.dish_id = dish_id;
    }

    public Integer getDiner_id() {
        return diner_id;
    }

    public void setDiner_id(Integer diner_id) {
        this.diner_id = diner_id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDish_desc() {
        return dish_desc;
    }

    public void setDish_desc(String dish_desc) {
        this.dish_desc = dish_desc;
    }

    public String getTiime_desc() {
        return tiime_desc;
    }

    public void setTiime_desc(String tiime_desc) {
        this.tiime_desc = tiime_desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public Integer getCancel_request() {
        return cancel_request;
    }

    public void setCancel_request(Integer cancel_request) {
        this.cancel_request = cancel_request;
    }
}
