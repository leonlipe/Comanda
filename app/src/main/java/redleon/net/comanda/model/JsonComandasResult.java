package redleon.net.comanda.model;

/**
 * Created by leon on 19/05/15.
 */
public class JsonComandasResult {

    private String status;
    private ComandasResult[] diners;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ComandasResult[] getDiners() {
        return diners;
    }

    public void setDiners(ComandasResult[] diners) {
        this.diners = diners;
    }
}
