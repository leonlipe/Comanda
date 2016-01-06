package redleon.net.comanda.model;

/**
 * Created by leon on 05/01/16.
 */
public class Table {

    private Integer id;
    private Integer number;

    public Table(){

    }

    public Table(Integer pId, Integer pNumber){
        this.id = pId;
        this.number = pNumber;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
