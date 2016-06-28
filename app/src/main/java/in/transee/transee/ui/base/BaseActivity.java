package in.transee.transee.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import in.transee.transee.R;
import in.transee.transee.injection.component.BaseActivityComponent;

/**
 * Created by Zhukov Michael.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private BaseActivityComponent activityComponent;

    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = createComponent();
    }

    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    protected void setToolbarTitle(@StringRes int title) {
        getSupportActionBar().setTitle(title);
    }

    public BaseActivityComponent getComponent() {
        return activityComponent;
    }

    public BaseActivityComponent createComponent() {
        return null;
    }
}
