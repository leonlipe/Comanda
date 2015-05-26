package redleon.net.comanda.model;

/**
 * Created by leon on 25/05/15.
 */
public class Tiime {

    private Integer id;
    private String name;
    private String description;

    private Dish[] items;

    public Tiime(){

    }

    public Tiime(Integer id, String name, String description, Dish[] items){
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = items;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Dish[] getItems() {
        return items;
    }

    public void setItems(Dish[] items) {
        this.items = items;
    }
}
