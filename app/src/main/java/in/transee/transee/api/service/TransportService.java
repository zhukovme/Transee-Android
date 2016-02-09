package in.transee.transee.api.service;

import java.util.List;

import in.transee.transee.model.transport.TransportType;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Michael Zhukov
 */
public interface TransportService {

    @GET("/api/v1/cities/{city}/")
    Call<List<TransportType>> transportTypes(@Path("city") String city);
}
