package in.transee.transee.data.transport;

import android.content.Context;

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
@DatabaseTable(tableName = "transports")
public class Transports {

    @DatabaseField(generatedId = true)
    private int _id;

    @SerializedName("type")
    @DatabaseField(canBeNull = false)
    private String type;

    @SerializedName("items")
    @ForeignCollectionField(eager = true)
    private Collection<TransportItem> items;

    Transports() {
    }

    public Transports(String type, Collection<TransportItem> items) {
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
        return new ArrayList<>(items);
    }
}
