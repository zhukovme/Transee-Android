package in.transee.transee.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import in.transee.transee.data.transport.Transports;

/**
 * @author Michael Zhukov
 */
public class TransportsDao extends BaseDaoImpl<Transports, Integer> {

    public TransportsDao(ConnectionSource connectionSource, Class<Transports> dataClass)
            throws SQLException {
        super(connectionSource, dataClass);
    }

    public void add(Transports transports) {
        try {
            this.create(transports);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transports> getAll(String city) {
        List<Transports> transportsList = null;
        try {
            transportsList = this.queryForEq(Transports.CITY_FIELD_NAME, city);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transportsList;
    }

    public void deleteAll() {
        try {
            TableUtils.clearTable(this.getConnectionSource(), Transports.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
