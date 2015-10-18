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
    private List<StationInfoTransport> transports;
    @SerializedName("forecasts")
    private List<StationInfoForecast> forecasts;

    public StationInfo(String name, List<StationInfoTransport> transports, List<StationInfoForecast> forecasts) {
        this.name = name;
        this.transports = transports;
        this.forecasts = forecasts;
    }

    public String getName() {
        return name;
    }

    public List<StationInfoTransport> getTransports() {
        return transports;
    }

    public List<StationInfoForecast> getForecasts() {
        return forecasts;
    }
}
