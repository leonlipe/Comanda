package redleon.net.comanda.model;

/**
 * Created by leon on 25/06/15.
 */
public class DishToOrder {

    private Integer serviceId;
    private Integer dinerId;
    private String notes;
    private Integer dishtiime;
    private Integer dishsize;
    private String[] picker;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getDinerId() {
        return dinerId;
    }

    public void setDinerId(Integer dinerId) {
        this.dinerId = dinerId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getDishtiime() {
        return dishtiime;
    }

    public void setDishtiime(Integer dishtiime) {
        this.dishtiime = dishtiime;
    }

    public Integer getDishsize() {
        return dishsize;
    }

    public void setDishsize(Integer dishsize) {
        this.dishsize = dishsize;
    }

    public String[] getPicker() {
        return picker;
    }

    public void setPicker(String[] picker) {
        this.picker = picker;
    }
}
