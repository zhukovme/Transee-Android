package in.transee.transee.api;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import in.transee.transee.api.service.CitiesService;
import in.transee.transee.api.service.PositionsService;
import in.transee.transee.api.service.RoutesService;
import in.transee.transee.api.service.StationInfoService;
import in.transee.transee.api.service.StationsService;
import in.transee.transee.api.service.TransportInfoService;
import in.transee.transee.api.service.TransportsService;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * @author Michael Zhukov
 */
public class ApiFactory {

    private static final String BASE_URL = "https://transee.in/";
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 60;

    private static final OkHttpClient CLIENT = new OkHttpClient();

    static {
        CLIENT.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
    }

    @NonNull
    public static RoutesService getRoutesService() {
        return getRetrofit().create(RoutesService.class);
    }

    @NonNull
    public static TransportsService getTransportsService() {
        return getRetrofit().create(TransportsService.class);
    }

    @NonNull
    public static StationsService getStationsService() {
        return getRetrofit().create(StationsService.class);
    }

    @NonNull
    public static PositionsService getPositionsService() {
        return getRetrofit().create(PositionsService.class);
    }

    @NonNull
    public static TransportInfoService getTransportsInfoService() {
        return getRetrofit().create(TransportInfoService.class);
    }

    @NonNull
    public static StationInfoService getStationsInfoService() {
        return getRetrofit().create(StationInfoService.class);
    }

    @NonNull
    public static CitiesService getCitiesService() {
        return getRetrofit().create(CitiesService.class);
    }

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(CLIENT)
                .build();
    }
}
