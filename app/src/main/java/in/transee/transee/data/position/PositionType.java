package in.transee.transee.data.position;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Michael Zhukov
 */
public class PositionType {

    @SerializedName("type")
    private String type;
    @SerializedName("items")
    private List<PositionItem> items;

    public PositionType(String type, List<PositionItem> items) {
        this.type = type;
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public List<PositionItem> getItems() {
        return items;
    }
}
