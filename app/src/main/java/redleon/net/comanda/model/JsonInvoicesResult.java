package redleon.net.comanda.model;

/**
 * Created by leon on 30/07/15.
 */
public class JsonInvoicesResult {

    private String status;
    private Invoice[] invoices;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Invoice[] getInvoices() {
        return invoices;
    }

    public void setInvoices(Invoice[] invoices) {
        this.invoices = invoices;
    }
}
