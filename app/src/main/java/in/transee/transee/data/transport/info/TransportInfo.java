package in.transee.transee.data.transport.info;

import com.google.gson.annotations.SerializedName;

/**
 * @author Michael Zhukov
 */
public class TransportInfo {

    @SerializedName("station")
    private String station;
    @SerializedName("time")
    private String time;

    public TransportInfo(String station, String time) {
        this.station = station;
        this.time = time;
    }

    public String getStation() {
        return station;
    }

    public String getTime() {
        return time;
    }
}
