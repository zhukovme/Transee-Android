package in.transee.transee.data.position;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * @author Michael Zhukov
 */
public class Vehicle {

    @SerializedName("gos_id")
    private String gosId;
    @SerializedName("angle")
    private int angle;
    @SerializedName("position")
    private double[] position;

    public Vehicle(String gosId, int angle, double[] position) {
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

    public LatLng getPosition() {
        return new LatLng(position[0], position[1]);
    }
}
