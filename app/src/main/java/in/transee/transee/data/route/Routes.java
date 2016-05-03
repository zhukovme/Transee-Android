package in.transee.transee.data.route;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Michael Zhukov
 */
@DatabaseTable(tableName = "routes")
public class Routes {

    @DatabaseField(generatedId = true)
    private int _id;

    @SerializedName("type")
    @DatabaseField(canBeNull = false)
    private String type;

    @SerializedName("items")
    @ForeignCollectionField(eager = true)
    private Collection<RouteItem> items;

    Routes() {
    }

    public Routes(String type, Collection<RouteItem> items) {
        this.type = type;
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public List<RouteItem> getItems() {
        return new ArrayList<>(items);
    }
}
