package in.transee.transee.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author Michael Zhukov
 */
public class ApiModule {

    private static final String BASE_URL = "http://178.62.248.26";

    public static ApiInterface getApiInterface() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(ApiInterface.class);
    }
}
