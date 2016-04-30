package in.transee.transee.api;

import java.util.List;

import in.transee.transee.data.position.Positions;
import in.transee.transee.data.route.Routes;
import in.transee.transee.data.station.Station;
import in.transee.transee.data.station.info.StationInfo;
import in.transee.transee.data.transport.Transports;
import in.transee.transee.data.transport.info.TransportInfo;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * @author Michael Zhukov
 */
public interface ApiInterface {

    @GET("api/v1/cities")
    Observable<List<String>> cities();

    @GET("api/v1/cities/{city}/coordinates")
    Observable<double[]> citiesCoordinates(@Path("city") String city);

    @GET("api/v1/cities/{city}")
    Observable<List<Transports>> transports(@Path("city") String city);

    @GET("api/v1/cities/{city}/routes")
    Observable<List<Routes>> routes(@Path("city") String city);

    @GET("api/v1/cities/{city}/positions")
    Observable<List<Positions>> positions(@Path("city") String city,
                                          @Query("type[]") String[] types,
                                          @Query("numbers[autobus][]") String[] buses,
                                          @Query("numbers[trolleybus][]") String[] trolleys,
                                          @Query("numbers[tram][]") String[] trams,
                                          @Query("numbers[minibus_taxi][]") String[] minibuses);

    @GET("api/v1/cities/{city}/transport_info")
    Observable<List<TransportInfo>> transportInfo(@Path("city") String city,
                                                  @Query("type") String type,
                                                  @Query("gos_id") String gosId);

    @GET("api/v1/cities/{city}/stations")
    Observable<List<Station>> stations(@Path("city") String city);

    @GET("api/v1/cities/{city}/station_info")
    Observable<StationInfo> stationInfo(@Path("city") String city, @Query("id") String id);
}
