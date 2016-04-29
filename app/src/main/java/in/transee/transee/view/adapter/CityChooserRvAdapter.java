package in.transee.transee.view.adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.transee.transee.R;
import in.transee.transee.data.city.City;
import in.transee.transee.view.activity.MapActivity;

/**
 * @author Michael Zhukov
 */
public class CityChooserRvAdapter extends RecyclerView.Adapter<CityChooserRvAdapter.ViewHolder> {

    private AppCompatActivity activity;

    private List<City> cityList;

    public CityChooserRvAdapter(AppCompatActivity activity) {
        this.activity = activity;
        cityList = new ArrayList<>();
    }

    public void setData(List<City> cityList) {
        this.cityList = cityList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_city_chooser, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        City city = cityList.get(position);
        holder.tvCity.setText(city.getName(holder.tvCity.getContext()));
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCity;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, MapActivity.class);
            intent.putExtra(MapActivity.CURRENT_CITY_EXTRA, cityList.get(getAdapterPosition()));
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
