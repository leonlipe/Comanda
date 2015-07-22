package redleon.net.comanda.model;

/**
 * Created by leon on 22/07/15.
 */
public class JsonMakersDetailResult {
    private String status;
    private Comanda command;
    private Dish[] dishes;
    private String status_desc;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Comanda getCommand() {
        return command;
    }

    public void setCommand(Comanda command) {
        this.command = command;
    }

    public Dish[] getDishes() {
        return dishes;
    }

    public void setDishes(Dish[] dishes) {
        this.dishes = dishes;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }
}
