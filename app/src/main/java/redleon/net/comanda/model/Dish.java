package redleon.net.comanda.model;

/**
 * Created by leon on 21/05/15.
 */
public class Dish {

    private Integer id;
    private String description;

    public Dish(){

    }

    public Dish(Integer id, String description){
        this.id = id;
        this.description = description;
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
}
