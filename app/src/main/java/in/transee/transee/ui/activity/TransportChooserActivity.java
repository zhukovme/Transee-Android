package in.transee.transee.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import in.transee.transee.R;
import in.transee.transee.model.TransportListItem;
import in.transee.transee.model.city.City;
import in.transee.transee.model.transport.TransportItem;
import in.transee.transee.model.transport.TransportType;
import in.transee.transee.ui.adapter.TransportChooserRvAdapter;

/**
 * @author Michael Zhukov
 */
public class TransportChooserActivity extends AppCompatActivity {

    public static final String SELECTED_TRANSPORT_EXTRA = "selected_transport_extra";

    private ViewPager viewPager;
    private PagerSlidingTabStrip tabs;
    private FloatingActionButton fabApplyTransport;

    private City currentCity;
    private List<RecyclerView> recyclerViews;
    private List<TransportType> transportTypes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_chooser);

        currentCity = (City) getIntent().getSerializableExtra(MapActivity.CURRENT_CITY_EXTRA);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(currentCity.getName(this));
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        fabApplyTransport = (FloatingActionButton) findViewById(R.id.fab_apply_transport);
        fabApplyTransport.hide();
    }

    public void onFabApplyTransportClick(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        HashMap<String, List<String>> selectedItems = new HashMap<>();
        for (RecyclerView rv : recyclerViews) {
            TransportChooserRvAdapter adapter = (TransportChooserRvAdapter) rv.getAdapter();
            selectedItems.put((String) rv.getTag(), adapter.getSelectedItems());
        }
        intent.putExtra(SELECTED_TRANSPORT_EXTRA, selectedItems);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    private List<RecyclerView> setupRecyclerViews() {
        if (transportTypes == null) {
            return Collections.emptyList();
        }
        List<RecyclerView> rvList = new ArrayList<>();
        for (TransportType type : transportTypes) {
            RecyclerView rv = new RecyclerView(this);
            rv.setAdapter(new TransportChooserRvAdapter(setupTransportList(type), fabApplyTransport));
            rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rv.setTag(type.getType());
            rvList.add(rv);
        }
        return rvList;
    }

    private List<TransportListItem> setupTransportList(TransportType type) {
        List<TransportListItem> transportList = new ArrayList<>();
        for (TransportItem item : type.getItems()) {
            TransportListItem transportListItem = new TransportListItem();
            transportListItem.setName(item.getName());
            transportListItem.setId(item.getId());
            transportList.add(transportListItem);
        }
        return transportList;
    }

    private List<String> setupTabTitles() {
        if (transportTypes == null) {
            return Collections.emptyList();
        }
        List<String> tabTitles = new ArrayList<>();
        for (TransportType type : transportTypes) {
            tabTitles.add(getString(
                    getResources().getIdentifier(type.getType(), "string", getPackageName())));
        }
        return tabTitles;
    }
}
