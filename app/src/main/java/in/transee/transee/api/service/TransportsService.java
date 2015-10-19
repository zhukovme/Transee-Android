package in.transee.transee.api.service;

import java.util.List;

import in.transee.transee.model.transport.TransportType;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Michael Zhukov
 */
public interface TransportsService {

    @GET("/{city}/")
    Call<List<TransportType>> transportTypes(@Path("city") String city);
}
