package in.transee.transee.data.transport;

import com.google.gson.annotations.SerializedName;

/**
 * @author Michael Zhukov
 */
public class TransportItem {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public TransportItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
