package in.transee.transee.api.service;

import java.util.List;

import in.transee.transee.model.position.PositionType;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Michael Zhukov
 */
public interface PositionsService {

    @GET("/api/v1/cities/{city}/positions/{rest}")
    Call<List<PositionType>> positionTypes(@Path("city") String city, @Path("rest") String rest);
}
