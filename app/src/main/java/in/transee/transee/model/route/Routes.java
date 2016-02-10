package in.transee.transee.model.route;

import java.util.List;

/**
 * @author Michael Zhukov
 */
public class Routes {

    private List<RouteType> routes;

    public Routes(List<RouteType> routeTypes) {
        this.routes = routeTypes;
    }

    public List<RouteType> getRoutes() {
        return routes;
    }
}
