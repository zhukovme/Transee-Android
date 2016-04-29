package in.transee.transee.data.station.info;

import com.google.gson.annotations.SerializedName;

/**
 * @author Michael Zhukov
 */
public class StationInfoTransport {

    @SerializedName("type")
    private String type;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;

    public StationInfoTransport(String type, String id, String name, String from, String to) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
