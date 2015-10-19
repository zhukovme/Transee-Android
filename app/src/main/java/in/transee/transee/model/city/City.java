package in.transee.transee.model.city;

import android.content.Context;

import in.transee.transee.model.LatLon;

/**
 * @author Michael Zhukov
 */
public class City {

    private String id;
    private LatLon coordinates;

    public City(String id, LatLon coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public String getName(Context ctx) {
        int resId = ctx.getResources().getIdentifier(id, "string", ctx.getPackageName());
        return ctx.getResources().getString(resId);
    }

    public LatLon getCoordinates() {
        return coordinates;
    }
}
