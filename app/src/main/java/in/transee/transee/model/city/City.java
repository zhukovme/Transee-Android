package in.transee.transee.model.city;

import android.content.Context;

import java.io.Serializable;

import in.transee.transee.model.LatLon;

/**
 * @author Michael Zhukov
 */
public class City implements Serializable {

    private String id;
    private double latitude;
    private double longitude;

    public City(String id, double[] coordinates) {
        this.id = id;
        this.latitude = coordinates[0];
        this.longitude = coordinates[1];
    }

    public String getId() {
        return id;
    }

    public String getName(Context ctx) {
        int resId = ctx.getResources().getIdentifier(id, "string", ctx.getPackageName());
        return ctx.getResources().getString(resId);
    }

    public LatLon getCoordinates() {
        return new LatLon(latitude, longitude);
    }
}
