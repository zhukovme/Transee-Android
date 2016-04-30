package in.transee.transee.data.transport;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Michael Zhukov
 */
public class Transports {

    @SerializedName("type")
    private String type;
    @SerializedName("items")
    private List<TransportItem> items;

    public Transports(String type, List<TransportItem> items) {
        this.type = type;
        this.items = items;
    }

    public String getName(Context context) {
        return context.getString(
                context.getResources().getIdentifier(type, "string", context.getPackageName()));
    }

    public String getType() {
        return type;
    }

    public List<TransportItem> getItems() {
        return items;
    }
}
