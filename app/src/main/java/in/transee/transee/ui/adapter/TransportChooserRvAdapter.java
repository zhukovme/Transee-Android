package in.transee.transee.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.List;

import in.transee.transee.R;
import in.transee.transee.data.transportListItem.TransportChooserObservable;
import in.transee.transee.data.transportListItem.TransportListItem;

import static in.transee.transee.api.Repository.TransportType.BUS;
import static in.transee.transee.api.Repository.TransportType.MINIBUS;
import static in.transee.transee.api.Repository.TransportType.TRAM;
import static in.transee.transee.api.Repository.TransportType.TROLLEY;

/**
 * @author Michael Zhukov
 */
public class TransportChooserRvAdapter extends RecyclerView.Adapter<TransportChooserRvAdapter.ItemHolder> {

    private Context context;
    private List<TransportListItem> transportItems;
    private TransportChooserObservable transportChooserObservable;
    private MultiSelector multiSelector;

    public TransportChooserRvAdapter(Context context, List<TransportListItem> transportItems,
                                     TransportChooserObservable transportChooserObservable) {
        this.context = context;
        this.transportItems = transportItems;
        this.transportChooserObservable = transportChooserObservable;
        multiSelector = new MultiSelector();
        multiSelector.setSelectable(true);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_transport_chooser, parent, false);
        return new ItemHolder(v, multiSelector);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        TransportListItem transportItem = transportItems.get(position);
        Drawable icon;
        switch (transportItem.getType()) {
            case BUS:
                icon = ContextCompat.getDrawable(context, R.drawable.ic_bus_grey600_24dp);
                break;
            case TROLLEY:
                icon = ContextCompat.getDrawable(context, R.drawable.ic_trolleybus_grey);
                break;
            case TRAM:
                icon = ContextCompat.getDrawable(context, R.drawable.ic_tram_grey600_24dp);
                break;
            case MINIBUS:
                icon = ContextCompat.getDrawable(context, R.drawable.ic_minibus_taxi_grey);
                break;
            default:
                icon = ContextCompat.getDrawable(context, R.drawable.ic_bus_grey600_24dp);
                break;
        }
        holder.transportIcon.setImageDrawable(icon);
        holder.transportName.setText(transportItem.getName());
    }

    @Override
    public int getItemCount() {
        return transportItems.size();
    }

    class ItemHolder extends SwappingHolder implements View.OnClickListener {

        private ImageView transportIcon;
        private TextView transportName;

        public ItemHolder(View itemView, MultiSelector multiSelector) {
            super(itemView, multiSelector);
            itemView.setOnClickListener(this);
            transportIcon = (ImageView) itemView.findViewById(R.id.iv_transport_icon);
            transportName = (TextView) itemView.findViewById(R.id.tv_transport_name);
        }

        @Override
        public void onClick(View v) {
            TransportListItem item = transportItems.get(getAdapterPosition());
            item.setIsChecked(!item.isChecked());
            if (item.isChecked()) {
                transportChooserObservable.addItem(item);
            } else {
                transportChooserObservable.deleteItem(item);
            }
            multiSelector.tapSelection(this);
        }
    }
}
