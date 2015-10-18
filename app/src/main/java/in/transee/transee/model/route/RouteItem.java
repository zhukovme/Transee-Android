package in.transee.transee.model.route;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import in.transee.transee.model.LatLon;

/**
 * @author Michael Zhukov
 */
public class RouteItem {

    @SerializedName("id")
    private String id;
    @SerializedName("route")
    private List<LatLon> route;

    public RouteItem(String id, List<LatLon> route) {
        this.id = id;
        this.route = route;
    }

    public String getId() {
        return id;
    }

    public List<LatLon> getRoute() {
        return route;
    }
}
