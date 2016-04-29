package in.transee.transee.data.station;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * @author Michael Zhukov
 */
public class Station {

    @SerializedName("id")
    private String id;
    @SerializedName("position")
    private double[] position;

    public Station(String id, double[] position) {
        this.id = id;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public LatLng getPosition() {
        return new LatLng(position[0], position[1]);
    }
}
