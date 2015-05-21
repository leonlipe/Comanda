package redleon.net.comanda.model;

/**
 * Created by leon on 19/05/15.
 */
public class JsonPaymentsResult {

    private String status;
    private PaymentsResult[] diners;
    private String gran_total;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaymentsResult[] getDiners() {
        return diners;
    }

    public void setDiners(PaymentsResult[] diners) {
        this.diners = diners;
    }

    public String getGran_total() {
        return gran_total;
    }

    public void setGran_total(String gran_total) {
        this.gran_total = gran_total;
    }
}
