package redleon.net.comanda;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;

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

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new TypiconsModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new MeteoconsModule())
                .with(new WeathericonsModule())
                .with(new SimpleLineIconsModule())
                .with(new IoniconsModule());
    }
}