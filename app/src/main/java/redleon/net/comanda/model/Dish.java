package redleon.net.comanda.model;

/**
 * Created by leon on 21/05/15.
 */
public class Dish {

    private Integer id;
    private String description;
    private String name;

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
}
