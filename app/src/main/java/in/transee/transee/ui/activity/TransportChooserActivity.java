package in.transee.transee.ui.activity;

import android.app.ProgressDialog;
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

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mTabs;
    private FloatingActionButton mFabApplyTransport;
    private ProgressDialog mProgressDialog;

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
        mFabApplyTransport = (FloatingActionButton) findViewById(R.id.fab_apply_transport);
        mFabApplyTransport.hide();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading_message));
        mProgressDialog.setCancelable(false);

        toolbar.setTitle(mCurrentCity.getName(this));
        setSupportActionBar(toolbar);

        mFabApplyTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MapActivity.class);
                HashMap<String, List<String>> selectedItems = new HashMap<>();
                for (RecyclerView rv : mRecyclerViews) {
                    TransportChooserRvAdapter adapter = (TransportChooserRvAdapter) rv.getAdapter();
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
            rv.setAdapter(new TransportChooserRvAdapter(setupTransportList(type), mFabApplyTransport));
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
}
