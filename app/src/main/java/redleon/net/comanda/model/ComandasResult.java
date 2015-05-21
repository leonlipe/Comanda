package redleon.net.comanda.model;

/**
 * Created by leon on 19/05/15.
 */
public class ComandasResult {

    private Integer id;
    private Integer diner_number;
    private Integer status;
    private String status_desc;
    private Integer pending;
    private Integer sended;
    private Integer finished;


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

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Integer getSended() {
        return sended;
    }

    public void setSended(Integer sended) {
        this.sended = sended;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }
}
