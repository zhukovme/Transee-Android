package in.transee.transee.api.service;

import java.util.List;

import in.transee.transee.model.transport.info.TransportInfo;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author Michael Zhukov
 */
public interface TransportInfoService {

    @GET("/{city}/transport_info")
    Call<List<TransportInfo>> transportInfo(@Path("city") String city, @Query("type") String type,
                                            @Query("gos_id") String gosId);
}
