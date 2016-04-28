package in.transee.transee.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import in.transee.transee.R;
import in.transee.transee.api.Repository;
import in.transee.transee.model.city.City;
import in.transee.transee.ui.activity.MapActivity;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * @author Michael Zhukov
 */
public class CityChooserFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startTransportActivity(position);
        getActivity().finish();
    }

    private void startTransportActivity(int cityPosition) {
        Intent intent = new Intent(getActivity(), MapActivity.class);
//        intent.putExtra(MapActivity.CURRENT_CITY_EXTRA, mCities.get(cityPosition));
        startActivity(intent);
    }

    private void fetchCitiesAndHandle() {
        Repository.INSTANCE.getCities()
                .doOnNext(new Action1<List<City>>() {
                    @Override
                    public void call(final List<City> cities) {
                        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getActivity(), MapActivity.class);
                                intent.putExtra(MapActivity.CURRENT_CITY_EXTRA, cities.get(position));
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
                    }
                })
                .subscribe();
    }

    private void onItemClick(City city) {

    }
}
