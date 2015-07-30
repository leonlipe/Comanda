package redleon.net.comanda.model;

/**
 * Created by leon on 30/07/15.
 */
public class Invoice {

    private Integer id;
    private String date_made;
    private String name;
    private String address;
    private String rfc;
    private Integer status;
    private Integer service_id;
    private String subtotal;
    private String gran_total;
    private String iva;
    private String iva_percent;
    private String discount;
    private String cfdil;
    private String cfd_identification;
    private String payment_form;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate_made() {
        return date_made;
    }

    public void setDate_made(String date_made) {
        this.date_made = date_made;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getService_id() {
        return service_id;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getGran_total() {
        return gran_total;
    }

    public void setGran_total(String gran_total) {
        this.gran_total = gran_total;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getIva_percent() {
        return iva_percent;
    }

    public void setIva_percent(String iva_percent) {
        this.iva_percent = iva_percent;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCfdil() {
        return cfdil;
    }

    public void setCfdil(String cfdil) {
        this.cfdil = cfdil;
    }

    public String getCfd_identification() {
        return cfd_identification;
    }

    public void setCfd_identification(String cfd_identification) {
        this.cfd_identification = cfd_identification;
    }

    public String getPayment_form() {
        return payment_form;
    }

    public void setPayment_form(String payment_form) {
        this.payment_form = payment_form;
    }
}
