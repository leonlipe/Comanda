package redleon.net.comanda.model;

/**
 * Created by leon on 21/05/15.
 */
public class Dish {

    private Integer id;
    private String description;
    private String name;
    private Integer status;
    private Integer order_dishes_id;
    private String place_name;
    private Integer place_id;
    private String status_desc;
    private String notes;
    private Extra[] extras;
    private Integer dishtiime;
    private String size_desc;
    private Integer cancel_request;




    public Dish(){

    }

    public Dish(Integer id, String description, String name){
        this.id = id;
        this.description = description;
        this.name = name;

    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrder_dishes_id() {
        return order_dishes_id;
    }

    public void setOrder_dishes_id(Integer order_dishes_id) {
        this.order_dishes_id = order_dishes_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public Integer getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Integer place_id) {
        this.place_id = place_id;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Extra[] getExtras() {
        return extras;
    }

    public void setExtras(Extra[] extras) {
        this.extras = extras;
    }

    public Integer getDishtiime() {
        return dishtiime;
    }

    public void setDishtiime(Integer dishtiime) {
        this.dishtiime = dishtiime;
    }

    public String getSize_desc() {
        return size_desc;
    }

    public void setSize_desc(String size_desc) {
        this.size_desc = size_desc;
    }

    public Integer getCancel_request() {
        return cancel_request;
    }

    public void setCancel_request(Integer cancel_request) {
        this.cancel_request = cancel_request;
    }
}
