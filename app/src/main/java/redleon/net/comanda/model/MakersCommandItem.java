package redleon.net.comanda.model;

/**
 * Created by leon on 17/07/15.
 */
public class MakersCommandItem {

    private Integer id;
    private String name;
    private Integer status;
    private String table;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
