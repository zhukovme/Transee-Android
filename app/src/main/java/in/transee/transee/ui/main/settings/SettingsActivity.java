package in.transee.transee.ui.main.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import in.transee.transee.R;
import in.transee.transee.TranseeApplication;
import in.transee.transee.api.Repository;
import in.transee.transee.injection.component.BaseActivityComponent;
import in.transee.transee.ui.base.BaseActivity;

/**
 * Created by Zhukov Michael.
 */
public class SettingsActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingsActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initToolbar();
        setToolbarTitle(R.string.settings);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new SettingsFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Inject
        Repository repository;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ((SettingsActivityComponent) createComponent()).inject(this);

            addPreferencesFromResource(R.xml.settings);

            Preference clearEventsCache = findPreference(getString(R.string.pref_clear_cache));
            clearEventsCache.setOnPreferenceClickListener(preference -> {
                repository.clearCache();
                Toast.makeText(getActivity(), "Cache cleared", Toast.LENGTH_SHORT).show();
                return true;
            });
        }

        public BaseActivityComponent createComponent() {
            return DaggerSettingsActivityComponent.builder()
                    .applicationComponent(TranseeApplication.get(getActivity()).getComponent())
                    .build();
        }
    }
}
