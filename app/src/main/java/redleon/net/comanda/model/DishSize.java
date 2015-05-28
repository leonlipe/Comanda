package redleon.net.comanda.model;

/**
 * Created by leon on 27/05/15.
 */
public class DishSize {

    private Integer id;
    private String description;

    public DishSize(){

    }

    public DishSize(Integer id, String description){
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
    @Override
    public String toString(){
        return getDescription();
    };
}
