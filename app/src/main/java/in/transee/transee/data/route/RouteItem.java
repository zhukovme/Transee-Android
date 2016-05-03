package in.transee.transee.data.route;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Zhukov
 */
@DatabaseTable(tableName = "route_items")
public class RouteItem {

    @DatabaseField(generatedId = true)
    private int _id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Routes routes;

    @SerializedName("id")
    @DatabaseField(canBeNull = false)
    private String id;

    @SerializedName("route")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private ArrayList<double[]> route;

    RouteItem() {
    }

    public RouteItem(String id, ArrayList<double[]> route) {
        this.id = id;
        this.route = route;
    }

    public RouteItem(Routes routes, String id, ArrayList<double[]> route) {
        this.routes = routes;
        this.id = id;
        this.route = route;
    }

    public String getId() {
        return id;
    }

    public ArrayList<double[]> getRouteDouble() {
        return route;
    }

    public List<LatLng> getRoute() {
        List<LatLng> points = new ArrayList<>();
        for (double[] point : route) {
            points.add(new LatLng(point[0], point[1]));
        }
        return points;
    }

    public Routes getRoutes() {
        return routes;
    }
}
