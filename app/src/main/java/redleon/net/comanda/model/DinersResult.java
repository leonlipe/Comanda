package redleon.net.comanda.model;

/**
 * Created by leon on 19/05/15.
 */
public class DinersResult {

    private Integer id;
    private Integer diner_number;
    private String diner_desc;
    private Integer status;
    private String status_desc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDiner_number() {
        return diner_number;
    }

    public void setDiner_number(Integer diner_number) {
        this.diner_number = diner_number;
    }

    public String getDiner_desc() {
        return diner_desc;
    }

    public void setDiner_desc(String diner_desc) {
        this.diner_desc = diner_desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }
}
