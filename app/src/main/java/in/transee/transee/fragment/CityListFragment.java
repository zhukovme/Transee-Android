package in.transee.transee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import in.transee.transee.R;
import in.transee.transee.activity.TransportActivity;

public class CityListFragment extends ListFragment {

    String[] mCities = new String[] {"Кострома", "Ярославль"};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.city_list_item,
                R.id.textView, mCities);
        getListView().setDivider(ContextCompat.getDrawable(getActivity(), R.color.transparent));
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(getActivity(), TransportActivity.class));
        getActivity().finish();
    }
}
