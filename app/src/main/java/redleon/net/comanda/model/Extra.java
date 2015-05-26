package redleon.net.comanda.model;

/**
 * Created by leon on 26/05/15.
 */
public class Extra {

    private String key;
    private String description;
    private Integer id;


    public Extra(){

    }

    public Extra(Integer id, String key, String description){
        this.id = id;
        this.key = key;
        this.description = description;


    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
