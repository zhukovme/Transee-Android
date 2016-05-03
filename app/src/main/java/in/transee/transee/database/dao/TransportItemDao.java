package in.transee.transee.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import in.transee.transee.data.transport.TransportItem;
import in.transee.transee.data.transport.Transports;

/**
 * @author Michael Zhukov
 */
public class TransportItemDao extends BaseDaoImpl<TransportItem, Integer> {

    public TransportItemDao(ConnectionSource connectionSource, Class<TransportItem> dataClass)
            throws SQLException {
        super(connectionSource, dataClass);
    }

    public void add(TransportItem transportItem) {
        try {
            this.create(transportItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addInTx(Iterable<TransportItem> itemList, Transports transports) {
        try {
            for (TransportItem item : itemList) {
                this.create(new TransportItem(transports, item.getId(), item.getName()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TransportItem> getAll() {
        List<TransportItem> transportItemList = null;
        try {
            transportItemList = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transportItemList;
    }

    public void deleteAll() {
        try {
            TableUtils.clearTable(this.getConnectionSource(), TransportItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
