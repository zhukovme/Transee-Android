package in.transee.transee.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import in.transee.transee.R;
import in.transee.transee.api.Repository;
import in.transee.transee.model.city.City;
import in.transee.transee.ui.activity.MapActivity;

/**
 * @author Michael Zhukov
 */
public class CityChooserFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setDivider(ContextCompat.getDrawable(getActivity(), R.color.transparent));
        getCitiesAndHandle();
    }

    private void startTransportActivity(City city) {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putExtra(MapActivity.CURRENT_CITY_EXTRA, city);
        startActivity(intent);
        getActivity().finish();
    }

    private void showErrorSnackbar() {
        Snackbar.make(getListView(), getString(R.string.not_available_msg), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry_snack_action, v -> {
                    getCitiesAndHandle();
                })
                .show();
    }

    private void getCitiesAndHandle() {
        Repository.INSTANCE.getCities()
                .doOnNext(cities ->
                        getListView().setOnItemClickListener((parent, view, position, id) ->
                                startTransportActivity(cities.get(position))))
                .map(cities -> {
                    List<String> citiesStrings = new ArrayList<>(cities.size());
                    for (City city : cities) {
                        citiesStrings.add(city.getName(getContext()));
                    }
                    return citiesStrings;})
                .subscribe(cities ->
                        setListAdapter(new ArrayAdapter(getContext(), R.layout.item_city_chooser,
                                R.id.tv_city, cities)),
                        throwable -> showErrorSnackbar());
    }
}
