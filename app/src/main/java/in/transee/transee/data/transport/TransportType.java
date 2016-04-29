package in.transee.transee.data.transport;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Michael Zhukov
 */
public class TransportType {

    @SerializedName("type")
    private String type;
    @SerializedName("items")
    private List<TransportItem> items;

    public TransportType(String type, List<TransportItem> items) {
        this.type = type;
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public List<TransportItem> getItems() {
        return items;
    }
}
