package in.transee.transee.data.route;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

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

    public List<LatLng> getRoute() {
        List<LatLng> points = new LinkedList<>();
        for (double[] point : route) {
            points.add(new LatLng(point[0], point[1]));
        }
        return points;
    }
}
