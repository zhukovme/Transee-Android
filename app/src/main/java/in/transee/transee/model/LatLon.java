package in.transee.transee.model;

/**
 * @author Michael Zhukov
 */
public class LatLon {

    private Double latitude;
    private Double longitude;

    public LatLon(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
