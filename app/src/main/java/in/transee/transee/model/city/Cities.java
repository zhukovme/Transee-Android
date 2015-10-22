package in.transee.transee.model.city;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Zhukov
 */
public class Cities implements Serializable {

    private List<City> cities;

    public Cities(List<City> cities) {
        this.cities = cities;
    }

    public List<City> getCities() {
        return cities;
    }

    public List<String> getNames(Context context) {
        List<String> names = new ArrayList<>();
        for (City city : cities) {
            names.add(city.getName(context));
        }
        return names;
    }

    public List<String> getIds() {
        List<String> ids = new ArrayList<>();
        for (City city : cities) {
            ids.add(city.getId());
        }
        return ids;
    }
}
