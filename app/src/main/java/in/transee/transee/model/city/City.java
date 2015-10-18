package in.transee.transee.model.city;

import in.transee.transee.model.LatLon;

/**
 * @author Michael Zhukov
 */
public class City {

    private String id;
    private String name;
    private LatLon coordinates;

    public City(String id, String name, LatLon coordinates) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LatLon getCoordinates() {
        return coordinates;
    }
}
