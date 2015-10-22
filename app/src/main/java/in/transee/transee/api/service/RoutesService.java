package in.transee.transee.api.service;

import java.util.List;

import in.transee.transee.model.route.RouteType;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Michael Zhukov
 */
public interface RoutesService {

    @GET("/api/v1/cities/{city}/routes")
    Call<List<RouteType>> routeTypes(@Path("city") String city);
}
