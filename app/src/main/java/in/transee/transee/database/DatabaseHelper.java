package in.transee.transee.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import in.transee.transee.data.city.City;
import in.transee.transee.data.route.RouteItem;
import in.transee.transee.data.route.Routes;
import in.transee.transee.data.transport.TransportItem;
import in.transee.transee.data.transport.Transports;
import in.transee.transee.database.dao.CityDao;
import in.transee.transee.database.dao.RouteItemDao;
import in.transee.transee.database.dao.RoutesDao;
import in.transee.transee.database.dao.TransportItemDao;
import in.transee.transee.database.dao.TransportsDao;

/**
 * @author Michael Zhukov
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "transee.db";

    private static final int DATABASE_VERSION = 1;

    private CityDao cityDao = null;
    private TransportsDao transportsDao = null;
    private TransportItemDao transportItemDao = null;
    private RoutesDao routesDao = null;
    private RouteItemDao routeItemDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, City.class);
            TableUtils.createTable(connectionSource, Transports.class);
            TableUtils.createTable(connectionSource, TransportItem.class);
            TableUtils.createTable(connectionSource, Routes.class);
            TableUtils.createTable(connectionSource, RouteItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, City.class, true);
            TableUtils.dropTable(connectionSource, Transports.class, true);
            TableUtils.dropTable(connectionSource, TransportItem.class, true);
            TableUtils.dropTable(connectionSource, Routes.class, true);
            TableUtils.dropTable(connectionSource, RouteItem.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CityDao getCityDao() {
        if (cityDao == null) {
            try {
                cityDao = new CityDao(getConnectionSource(), City.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cityDao;
    }

    public TransportsDao getTransportsDao() {
        if (transportsDao == null) {
            try {
                transportsDao = new TransportsDao(getConnectionSource(), Transports.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transportsDao;
    }

    public TransportItemDao getTransportItemDao() {
        if (transportItemDao == null) {
            try {
                transportItemDao = new TransportItemDao(getConnectionSource(), TransportItem.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transportItemDao;
    }

    public RoutesDao getRoutesDao() {
        if (routesDao == null) {
            try {
                routesDao = new RoutesDao(getConnectionSource(), Routes.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return routesDao;
    }
    public RouteItemDao getRouteItemDao() {
        if (routeItemDao == null) {
            try {
                routeItemDao = new RouteItemDao(getConnectionSource(), RouteItem.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return routeItemDao;
    }
}
