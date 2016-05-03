package in.transee.transee.data.city;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author Michael Zhukov
 */
@DatabaseTable(tableName = "cities")
public class City implements Serializable {

    @DatabaseField(generatedId = true)
    private int _id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String id;

    @DatabaseField(canBeNull = false, dataType = DataType.DOUBLE)
    private double latitude;

    @DatabaseField(canBeNull = false, dataType = DataType.DOUBLE)
    private double longitude;

    City() {
    }

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

    public LatLng getCoordinates() {
        return new LatLng(latitude, longitude);
    }
}
