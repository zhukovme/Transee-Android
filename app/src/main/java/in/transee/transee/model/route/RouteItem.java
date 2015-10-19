package in.transee.transee.model.route;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

import in.transee.transee.model.LatLon;

/**
 * @author Michael Zhukov
 */
public class RouteItem {

    @SerializedName("id")
    private String id;
    @SerializedName("route")
    private LinkedList<double[]> route;

    public RouteItem(String id, LinkedList<double[]> route) {
        this.id = id;
        this.route = route;
    }

    public String getId() {
        return id;
    }

    public List<LatLon> getRoute() {
        List<LatLon> points = new LinkedList<>();
        for (double[] point : route) {
            points.add(new LatLon(point[0], point[1]));
        }
        return points;
    }
}
