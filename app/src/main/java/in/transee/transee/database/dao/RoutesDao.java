package in.transee.transee.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import in.transee.transee.data.route.Routes;

/**
 * @author Michael Zhukov
 */
public class RoutesDao extends BaseDaoImpl<Routes, Integer> {

    public RoutesDao(ConnectionSource connectionSource, Class<Routes> dataClass)
            throws SQLException {
        super(connectionSource, dataClass);
    }

    public void add(Routes routes) {
        try {
            this.create(routes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Routes> getAll(String city) {
        List<Routes> routesList = null;
        try {
            routesList = this.queryForEq(Routes.CITY_FIELD_NAME, city);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routesList;
    }

    public void deleteAll() {
        try {
            TableUtils.clearTable(this.getConnectionSource(), Routes.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
