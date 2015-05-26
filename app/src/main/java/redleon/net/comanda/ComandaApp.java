package redleon.net.comanda;

import android.app.Application;

import java.util.List;

import redleon.net.comanda.model.Tiime;

/**
 * Created by leon on 25/05/15.
 */
public class ComandaApp extends Application {

    private List<Tiime> menu;


    public List<Tiime> getMenu() {
        return menu;
    }

    public void setMenu(List<Tiime> menu) {
        this.menu = menu;
    }
}