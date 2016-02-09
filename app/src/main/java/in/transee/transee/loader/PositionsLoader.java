package in.transee.transee.loader;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.transee.transee.api.ApiFactory;
import in.transee.transee.api.response.RequestResult;
import in.transee.transee.api.response.Response;
import in.transee.transee.api.service.PositionsService;
import in.transee.transee.model.position.PositionType;
import retrofit.Call;

/**
 * @author Michael Zhukov
 */
public class PositionsLoader extends BaseLoader {

    private static final String BUS_TYPE = "autobus";
    private static final String TROLLEY_TYPE = "trolleybus";
    private static final String TRAM_TYPE = "tram";
    private static final String SHUTTLE_BUS_TYPE = "autominibus_taxibus";

    private final String mCity;
    private final HashMap<String, List<String>> mTransportIds;

    private String[] mTypes = null;
    private String[] mBuses = null;
    private String[] mTrolleys = null;
    private String[] mTrams = null;
    private String[] mShuttleBuses = null;

    public PositionsLoader(Context context, String city, HashMap<String, List<String>> transportIds) {
        super(context);
        mCity = city;
        mTransportIds = transportIds;
    }

    @Override
    protected Response apiCall() throws IOException {
        PositionsService service = ApiFactory.getPositionsService();
        parseTransport();
        Call<List<PositionType>> call = service.positionTypes(mCity, mTypes, mBuses, mTrolleys,
                mTrams, mShuttleBuses);
        List<PositionType> positionTypes = call.execute().body();
        return new Response()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(positionTypes);
    }

    private void parseTransport() {
        List<String> types = new ArrayList<>();
        for (String type : mTransportIds.keySet()) {
            if (mTransportIds.get(type).isEmpty()) {
                continue;
            }
            types.add(type);
            switch (type) {
                case BUS_TYPE:
                    List<String> busIds = mTransportIds.get(type);
                    mBuses = busIds.toArray(new String[busIds.size()]);
                    break;
                case TROLLEY_TYPE:
                    List<String> trolleyIds = mTransportIds.get(type);
                    mTrolleys = trolleyIds.toArray(new String[trolleyIds.size()]);
                    break;
                case TRAM_TYPE:
                    List<String> tramIds = mTransportIds.get(type);
                    mTrams = tramIds.toArray(new String[tramIds.size()]);
                    break;
                case SHUTTLE_BUS_TYPE:
                    List<String> shuttleBusIds = mTransportIds.get(type);
                    mShuttleBuses = shuttleBusIds.toArray(new String[shuttleBusIds.size()]);
                    break;
            }
        }
        mTypes = types.toArray(new String[types.size()]);
    }
}
