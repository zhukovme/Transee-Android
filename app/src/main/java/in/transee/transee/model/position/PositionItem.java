package in.transee.transee.model.position;

import com.google.gson.annotations.SerializedName;

/**
 * @author Michael Zhukov
 */
public class PositionItem {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("items")
    private Vehicle vehicles;

    public PositionItem(String id, String name, Vehicle vehicles) {
        this.id = id;
        this.name = name;
        this.vehicles = vehicles;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Vehicle getVehicles() {
        return vehicles;
    }
}
