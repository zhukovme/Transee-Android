package in.transee.transee.api.service;

import java.util.List;

import in.transee.transee.model.station.Station;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Michael Zhukov
 */
public interface StationsService {

    @GET("/{city}/stations")
    Call<List<Station>> stations(@Path("city") String city);
}
