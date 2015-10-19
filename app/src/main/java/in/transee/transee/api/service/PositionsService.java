package in.transee.transee.api.service;

import java.util.List;

import in.transee.transee.model.position.PositionType;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author Michael Zhukov
 */
public interface PositionsService {

    @GET("/{city}/positions")
    Call<List<PositionType>> positionTypes(@Path("city") String city,
                                           @Query("type[]") String[] types,
                                           @Query("numbers[autobus][]") String[] buses,
                                           @Query("numbers[trolleybus][]") String[] trolleys,
                                           @Query("numbers[tram][]") String[] trams,
                                           @Query("numbers[minibus_taxi][]") String[] taxi);
}
