package redleon.net.comanda.model;

/**
 * Created by leon on 19/05/15.
 */
public class JsonDinersResult {

    private String status;
    private DinersResult[] diners;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DinersResult[] getDiners() {
        return diners;
    }

    public void setDiners(DinersResult[] diners) {
        this.diners = diners;
    }
}
