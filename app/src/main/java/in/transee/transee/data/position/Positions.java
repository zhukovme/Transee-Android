package in.transee.transee.data.position;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Michael Zhukov
 */
public class Positions {

    @SerializedName("type")
    private String type;
    @SerializedName("items")
    private List<PositionItem> items;

    public Positions(String type, List<PositionItem> items) {
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

    public List<PositionItem> getItems() {
        return items;
    }
}
