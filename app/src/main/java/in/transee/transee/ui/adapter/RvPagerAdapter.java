package in.transee.transee.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Michael Zhukov
 */
public class RvPagerAdapter extends PagerAdapter {

    private List<RecyclerView> pages;
    private List<String> tabTitles;

    public RvPagerAdapter(List<RecyclerView> pages, List<String> tabTitles) {
        this.pages = pages;
        this.tabTitles = tabTitles;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View page = pages.get(position);
        container.addView(page);
        return page;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
