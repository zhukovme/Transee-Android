package in.transee.transee.ui.fragment;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import in.transee.transee.R;
import in.transee.transee.api.response.Response;
import in.transee.transee.loader.CitiesLoader;
import in.transee.transee.model.city.Cities;
import in.transee.transee.model.city.City;
import in.transee.transee.ui.activity.MapActivity;

/**
 * @author Michael Zhukov
 */
public class CityListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Response> {

    private List<City> mCities;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getLoaderManager().initLoader(R.integer.cities_loader, Bundle.EMPTY, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startTransportActivity(position);
        getActivity().finish();
    }

    @Override
    public Loader<Response> onCreateLoader(int id, Bundle args) {
        return new CitiesLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Response> loader, Response data) {
        Cities cities = data.getTypedAnswer();
        List<String> citiesNames = cities.getNames(getContext());
        mCities = cities.getCities();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_city_list,
                R.id.textView, citiesNames);
        getListView().setDivider(ContextCompat.getDrawable(getActivity(), R.color.transparent));
        setListAdapter(adapter);

        getActivity().getLoaderManager().destroyLoader(loader.getId());
    }

    @Override
    public void onLoaderReset(Loader<Response> loader) {

    }

    private void startTransportActivity(int cityPosition) {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putExtra(MapActivity.CURRENT_CITY_EXTRA, mCities.get(cityPosition));
        startActivity(intent);
    }
}
