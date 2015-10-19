package in.transee.transee.api.service;

import in.transee.transee.model.station.info.StationInfo;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author Michael Zhukov
 */
public interface StationInfoService {

    @GET("/{city}/station_info")
    Call<StationInfo> stationInfo(@Path("city") String city, @Query("id") String id);
}
