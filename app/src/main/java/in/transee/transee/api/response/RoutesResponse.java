package in.transee.transee.api.response;

import android.content.Context;

import java.util.List;

import in.transee.transee.model.route.RouteType;

/**
 * @author Michael Zhukov
 */
public class RoutesResponse extends Response {

    @Override
    public void save(Context context) {
        List<RouteType> routeTypes = getTypedAnswer();
        if (routeTypes != null) {
            // save to database
        }
    }
}
