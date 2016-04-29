package in.transee.transee.data.transportListItem;

/**
 * @author Michael Zhukov
 */
public class TransportListItem {

    private String type;
    private String name;
    private String id;
    private boolean isChecked;

    public TransportListItem() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
