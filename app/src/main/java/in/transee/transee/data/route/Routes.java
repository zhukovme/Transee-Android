package in.transee.transee.data.route;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Michael Zhukov
 */
public class Routes {

    @SerializedName("type")
    private String type;
    @SerializedName("items")
    private List<RouteItem> items;

    public Routes(String type, List<RouteItem> items) {
        this.type = type;
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public List<RouteItem> getItems() {
        return items;
    }
}
