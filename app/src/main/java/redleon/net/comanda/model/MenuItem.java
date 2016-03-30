package redleon.net.comanda.model;

/**
 * Created by leon on 30/07/15.
 */
public class MenuItem {
    private Integer id;
    private String name;
    private String description;
    private boolean visible;
    private boolean wextras;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isWextras() {
        return wextras;
    }

    public void setWextras(boolean wextras) {
        this.wextras = wextras;
    }
}
