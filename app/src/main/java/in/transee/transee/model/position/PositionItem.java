package in.transee.transee.model.position;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Michael Zhukov
 */
public class PositionItem {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("items")
    private List<Vehicle> vehicles;

    public PositionItem(String id, String name, List<Vehicle> vehicles) {
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

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
