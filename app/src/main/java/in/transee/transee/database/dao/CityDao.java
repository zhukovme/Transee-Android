package in.transee.transee.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import in.transee.transee.data.city.City;

/**
 * @author Michael Zhukov
 */
public class CityDao extends BaseDaoImpl<City, Integer> {

    public CityDao(ConnectionSource connectionSource, Class<City> dataClass)
            throws SQLException {
        super(connectionSource, dataClass);
    }

    public void addInTx(List<City> cities) {
        try {
            for (City city : cities) {
                this.create(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<City> getAll() {
        List<City> cities = null;
        try {
            cities = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public void deleteAll() {
        try {
            TableUtils.clearTable(getConnectionSource(), City.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
