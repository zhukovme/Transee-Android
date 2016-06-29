package in.transee.transee.ui.main.city_chooser;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.transee.transee.R;
import in.transee.transee.data.city.City;
import in.transee.transee.ui.main.map.MapActivity;

/**
 * @author Michael Zhukov
 */
public class CityChooserRvAdapter extends RecyclerView.Adapter<CityChooserRvAdapter.CityChooserVH> {

    private final Activity activity;
    private List<City> cities;

    @Inject
    public CityChooserRvAdapter(Activity activity) {
        this.activity = activity;
        cities = new ArrayList<>();
    }

    public void setData(List<City> cityList) {
        this.cities = cityList;
        notifyDataSetChanged();
    }

    public void clear() {
        this.cities.clear();
        notifyDataSetChanged();
    }

    @Override
    public CityChooserVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_city_chooser, parent, false);
        return new CityChooserVH(v);
    }

    @Override
    public void onBindViewHolder(CityChooserVH holder, int position) {
        City city = cities.get(position);
        holder.tvCity.setText(city.getName(holder.tvCity.getContext()));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class CityChooserVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCity;

        public CityChooserVH(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city);
        }

        @Override
        public void onClick(View v) {
            City city = cities.get(getAdapterPosition());
            Intent intent = new Intent(activity, MapActivity.class);
            intent.putExtra(MapActivity.CURRENT_CITY_EXTRA, city);
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
