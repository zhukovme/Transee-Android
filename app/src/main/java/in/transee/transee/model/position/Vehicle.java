package in.transee.transee.model.position;

import com.google.gson.annotations.SerializedName;

import in.transee.transee.model.LatLon;

/**
 * @author Michael Zhukov
 */
public class Vehicle {

    @SerializedName("gos_id")
    private String gosId;
    @SerializedName("angle")
    private int angle;
    @SerializedName("position")
    private LatLon position;

    public Vehicle(String gosId, int angle, LatLon position) {
        this.gosId = gosId;
        this.angle = angle;
        this.position = position;
    }

    public String getGosId() {
        return gosId;
    }

    public int getAngle() {
        return angle;
    }

    public LatLon getPosition() {
        return position;
    }
}
