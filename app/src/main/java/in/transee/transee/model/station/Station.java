package in.transee.transee.model.station;

import com.google.gson.annotations.SerializedName;

import in.transee.transee.model.LatLon;

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

    public LatLon getPosition() {
        return new LatLon(position[0], position[1]);
    }
}
