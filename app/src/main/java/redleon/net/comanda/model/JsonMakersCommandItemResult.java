package redleon.net.comanda.model;

/**
 * Created by leon on 17/07/15.
 */
public class JsonMakersCommandItemResult {

    private MakersCommandItem[] comandas;
    private Place place;
    private String status;
    private String started;

    public MakersCommandItem[] getComandas() {
        return comandas;
    }

    public void setComandas(MakersCommandItem[] comandas) {
        this.comandas = comandas;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }
}
