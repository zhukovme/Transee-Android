package in.transee.transee.data.transport;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Michael Zhukov
 */
@DatabaseTable(tableName = "transport_items")
public class TransportItem {

    @DatabaseField(generatedId = true)
    private int _id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Transports transports;

    @SerializedName("id")
    @DatabaseField(canBeNull = false)
    private String id;

    @SerializedName("name")
    @DatabaseField(canBeNull = false)
    private String name;

    TransportItem() {
    }

    public TransportItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public TransportItem(Transports transports, String id, String name) {
        this.transports = transports;
        this.id = id;
        this.name = name;
    }

    public Transports getTransports() {
        return transports;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
