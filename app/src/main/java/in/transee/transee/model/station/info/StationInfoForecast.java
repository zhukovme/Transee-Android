package in.transee.transee.model.station.info;

import com.google.gson.annotations.SerializedName;

/**
 * @author Michael Zhukov
 */
public class StationInfoForecast {

    @SerializedName("type")
    private String type;
    @SerializedName("arrived_after")
    private String arriveAfter;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public StationInfoForecast(String type, String arriveAfter, String id, String name) {
        this.type = type;
        this.arriveAfter = arriveAfter;
        this.id = id;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getArriveAfter() {
        return arriveAfter;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
