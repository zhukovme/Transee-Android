package in.transee.transee.api.service;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Michael Zhukov
 */
public interface CitiesService {

    @GET("/")
    Call<List<String>> cities();

    @GET("/{city}/coordinates")
    Call<double[]> coordinates(@Path("city") String city);
}
