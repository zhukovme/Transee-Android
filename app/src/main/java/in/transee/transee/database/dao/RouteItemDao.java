package in.transee.transee.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import in.transee.transee.data.route.RouteItem;
import in.transee.transee.data.route.Routes;

/**
 * @author Michael Zhukov
 */
public class RouteItemDao extends BaseDaoImpl<RouteItem, Integer> {

    public RouteItemDao(ConnectionSource connectionSource, Class<RouteItem> dataClass)
            throws SQLException {
        super(connectionSource, dataClass);
    }

    public void addInTx(Iterable<RouteItem> itemList, Routes routes) {
        try {
            for (RouteItem item : itemList) {
                this.create(new RouteItem(routes, item.getId(), item.getRouteDouble()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            TableUtils.clearTable(this.getConnectionSource(), RouteItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
