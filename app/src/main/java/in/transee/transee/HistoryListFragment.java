package in.transee.transee;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HistoryListFragment extends ListFragment {

    DrawerLayout drawer;
    String[] testStrings = new String[] {"47 маршрутное такси", "ул. Рыкачева", "72 автобус", "┻━┻ ヘ╰( •̀ε•́ ╰)"};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater(savedInstanceState);
        ViewGroup navHeader = (ViewGroup)inflater.inflate(
                R.layout.nav_header_main, getListView(), false);
        ViewGroup listHeader = (ViewGroup)inflater.inflate(
                R.layout.history_list_header, getListView(), false);

        getListView().addHeaderView(navHeader, null, false);
        getListView().addHeaderView(listHeader, null, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.nav_item_main,
                R.id.textView, testStrings);
        getListView().setAdapter(adapter);

        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_list_fragment, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        position -= l.getHeaderViewsCount();
        Toast.makeText(getActivity(), "Position = " + position + " id = " + id, Toast.LENGTH_SHORT).show();
        drawer.closeDrawer(GravityCompat.START);
    }
}
