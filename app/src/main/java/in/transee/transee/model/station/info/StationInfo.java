package in.transee.transee.model.station.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Michael Zhukov
 */
public class StationInfo {

    @SerializedName("name")
    private String name;
    @SerializedName("transports")
    private List<StationInfoTransport> transportList;
    @SerializedName("forecasts")
    private List<StationInfoForecast> forecasts;

    public StationInfo(String name, List<StationInfoTransport> transportList, List<StationInfoForecast> forecasts) {
        this.name = name;
        this.transportList = transportList;
        this.forecasts = forecasts;
    }

    public String getName() {
        return name;
    }

    public List<StationInfoTransport> getTransportList() {
        return transportList;
    }

    public List<StationInfoForecast> getForecasts() {
        return forecasts;
    }
}
