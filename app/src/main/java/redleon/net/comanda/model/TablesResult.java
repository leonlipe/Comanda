package redleon.net.comanda.model;

import java.util.ArrayList;


/**
 * Created by leon on 24/04/15.
 */
public class TablesResult {
   // {"id":1,"number":1,"description":"Mesa 01","key":"M01","status":true,"status_desc":"Ocupada","sent":0,"prepared":2,"served":0}

    private Integer id;
    private Integer number;
    private String description;
    private String key;
    private boolean status;
    private String status_desc;
    private Integer sent;
    private Integer prepared;
    private Integer served;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public Integer getSent() {
        return sent;
    }

    public void setSent(Integer sent) {
        this.sent = sent;
    }

    public Integer getPrepared() {
        return prepared;
    }

    public void setPrepared(Integer prepared) {
        this.prepared = prepared;
    }

    public Integer getServed() {
        return served;
    }

    public void setServed(Integer served) {
        this.served = served;
    }
}
