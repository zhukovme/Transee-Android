package in.transee.transee.ui.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
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
import in.transee.transee.api.response.Response;
import in.transee.transee.loader.TransportsLoader;
import in.transee.transee.model.TransportListItem;
import in.transee.transee.model.city.City;
import in.transee.transee.model.transport.TransportItem;
import in.transee.transee.model.transport.TransportType;
import in.transee.transee.ui.adapter.ListViewPagerAdapter;
import in.transee.transee.ui.adapter.TransportRvListAdapter;

/**
 * @author Michael Zhukov
 */
public class TransportChooserActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Response> {

    public static final String SELECTED_TRANSPORT_EXTRA = "selected_transport_extra";

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mTabs;
    private FloatingActionButton mFabApplyTransports;

    private City mCurrentCity;
    private List<RecyclerView> mRecyclerViews;
    private List<TransportType> mTransportTypes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_chooser);

        mCurrentCity = (City) getIntent().getSerializableExtra(MapActivity.CURRENT_CITY_EXTRA);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mFabApplyTransports = (FloatingActionButton) findViewById(R.id.fab_apply_transports);
        mFabApplyTransports.hide();

        toolbar.setTitle(mCurrentCity.getName(this));
        setSupportActionBar(toolbar);

        getLoaderManager().initLoader(R.integer.transports_loader, Bundle.EMPTY, this);

        mFabApplyTransports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MapActivity.class);
                HashMap<String, List<String>> selectedItems = new HashMap<>();
                for (RecyclerView rv : mRecyclerViews) {
                    TransportRvListAdapter adapter = (TransportRvListAdapter) rv.getAdapter();
                    selectedItems.put((String) rv.getTag(), adapter.getSelectedItems());
                }
                intent.putExtra(SELECTED_TRANSPORT_EXTRA, selectedItems);
                setResult(RESULT_OK, intent);
                onBackPressed();
            }
        });
    }

    private List<RecyclerView> setupRecyclerViews() {
        if (mTransportTypes == null) {
            return Collections.emptyList();
        }
        List<RecyclerView> rvList = new ArrayList<>();
        for (TransportType type : mTransportTypes) {
            RecyclerView rv = new RecyclerView(this);
            rv.setAdapter(new TransportRvListAdapter(setupTransportList(type), mFabApplyTransports));
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
        if (mTransportTypes == null) {
            return Collections.emptyList();
        }
        List<String> tabTitles = new ArrayList<>();
        for (TransportType type : mTransportTypes) {
            tabTitles.add(getString(
                    getResources().getIdentifier(type.getType(), "string", getPackageName())));
        }
        return tabTitles;
    }

    @Override
    public Loader<Response> onCreateLoader(int id, Bundle args) {
        return new TransportsLoader(this, mCurrentCity.getId());
    }

    @Override
    public void onLoadFinished(Loader<Response> loader, Response data) {
        mTransportTypes = data.getTypedAnswer();
        mRecyclerViews = setupRecyclerViews();
        mViewPager.setAdapter(new ListViewPagerAdapter(mRecyclerViews, setupTabTitles()));
        mTabs.setViewPager(mViewPager);

        getLoaderManager().destroyLoader(loader.getId());
    }

    @Override
    public void onLoaderReset(Loader<Response> loader) {
    }
}
