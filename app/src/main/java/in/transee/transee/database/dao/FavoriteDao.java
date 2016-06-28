package in.transee.transee.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import in.transee.transee.data.favorite.Favorite;

/**
 * @author Michael Zhukov
 */
public class FavoriteDao extends BaseDaoImpl<Favorite, Integer> {

    public FavoriteDao(ConnectionSource connectionSource, Class<Favorite> dataClass)
            throws SQLException {
        super(connectionSource, dataClass);
    }

    public void add(Favorite favorite) {
        try {
            this.create(favorite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isFavorite(String city, String name) {
        List favorites = null;
        try {
            favorites = queryBuilder().where()
                    .eq(Favorite.CITY_FIELD_NAME, city)
                    .and()
                    .eq(Favorite.NAME_FIELD_NAME, name)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorites != null && !favorites.isEmpty();
    }

    public List<Favorite> getAll(String city) {
        List<Favorite> favorites = null;
        try {
            favorites = this.queryForEq(Favorite.CITY_FIELD_NAME, city);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorites;
    }

    public void deleteItem(String city, String name) {
        try {
            List<Favorite> favorites = queryBuilder().where()
                    .eq(Favorite.CITY_FIELD_NAME, city)
                    .and()
                    .eq(Favorite.NAME_FIELD_NAME, name)
                    .query();
            if (favorites != null && !favorites.isEmpty()) {
                delete(favorites);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            TableUtils.clearTable(getConnectionSource(), Favorite.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}