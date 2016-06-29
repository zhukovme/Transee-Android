package in.transee.transee.ui.main.map;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.transee.transee.R;
import in.transee.transee.data.favorite.Favorite;

/**
 * Created by Zhukov Michael.
 */
public class FavoritesRvAdapter extends RecyclerView.Adapter<FavoritesRvAdapter.FavoritesVH> {

    private final Activity activity;
    private final MapPresenter mapPresenter;
    private List<Favorite> favorites;

    public FavoritesRvAdapter(Activity activity, MapPresenter mapPresenter) {
        this.activity = activity;
        this.mapPresenter = mapPresenter;
        this.favorites = new ArrayList<>();
    }

    public void setData(List<Favorite> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    public void clear() {
        favorites.clear();
        notifyDataSetChanged();
    }

    @Override
    public FavoritesVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_favorites, parent, false);
        return new FavoritesVH(view);
    }

    @Override
    public void onBindViewHolder(FavoritesVH holder, int position) {
        Favorite favorite = favorites.get(position);
        holder.tvName.setText(favorite.getName());
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class FavoritesVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName;

        public FavoritesVH(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }

        @Override
        public void onClick(View view) {
            Favorite favorite = favorites.get(getAdapterPosition());
            mapPresenter.setTransportIds(favorite.getMapId());
            mapPresenter.loadTransport();
            activity.onBackPressed();
        }
    }
}
