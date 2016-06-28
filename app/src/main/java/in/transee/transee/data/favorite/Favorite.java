package in.transee.transee.data.favorite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Michael Zhukov
 */
@DatabaseTable(tableName = "favorites")
public class Favorite {

    public static final String NAME_FIELD_NAME = "name";
    public static final String CITY_FIELD_NAME = "city";

    @DatabaseField(generatedId = true)
    private int _id;

    @DatabaseField(canBeNull = false)
    private String city;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String type;

    @DatabaseField(canBeNull = false)
    private String itemId;

    Favorite() {
    }

    public Favorite(String city, String name) {
        this.city = city;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public HashMap<String, List<String>> getMapId() {
        List<String> id = new ArrayList<String>() {{ add(itemId); }};
        return new HashMap<String, List<String>>() {{ put(type, id); }};
    }
}