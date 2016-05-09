package in.transee.transee.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import in.transee.transee.R;

/**
 * @author Michael Zhukov
 */
public class HistoryListFragment extends ListFragment {

    String[] testStrings = new String[] {"47 маршрутное такси", "ул. Рыкачева", "72 автобус"};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater(savedInstanceState);
        ViewGroup navHeader = (ViewGroup)inflater.inflate(
                R.layout.nav_header_main, getListView(), false);
        ViewGroup listHeader = (ViewGroup)inflater.inflate(
                R.layout.header_history_list, getListView(), false);

        getListView().addHeaderView(navHeader, null, false);
        getListView().addHeaderView(listHeader, null, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.nav_item_main,
                R.id.textView, testStrings);
        getListView().setDivider(ContextCompat.getDrawable(getActivity(), R.color.transparent));
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Snackbar.make(v, "Position = " + position + " id = " + id, Snackbar.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }
}
