package in.transee.transee.api.response;

import android.content.Context;

import java.util.List;

import in.transee.transee.model.station.Station;

/**
 * @author Michael Zhukov
 */
public class StationsResponse extends Response {

    @Override
    public void save(Context context) {
        List<Station> stations = getTypedAnswer();
        if (stations != null) {
            // save to database
        }
    }
}
