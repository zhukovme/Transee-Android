package in.transee.transee.view.adapter;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.ArrayList;
import java.util.List;

import in.transee.transee.R;
import in.transee.transee.data.TransportListItem;

/**
 * @author Michael Zhukov
 */
public class TransportChooserRvAdapter extends RecyclerView.Adapter<TransportChooserRvAdapter.ItemHolder> {

    private List<TransportListItem> transportItems;
    private FloatingActionButton fabApplyTransport;
    private MultiSelector multiSelector;

    public TransportChooserRvAdapter(List<TransportListItem> transportItems,
                                     FloatingActionButton fabApplyTransport) {
        this.transportItems = transportItems;
        multiSelector = new MultiSelector();
        multiSelector.setSelectable(true);
        this.fabApplyTransport = fabApplyTransport;
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
        holder.transportName.setText(transportItem.getName());
    }

    @Override
    public int getItemCount() {
        return transportItems.size();
    }

    public List<String> getSelectedItems() {
        List<String> selectedItems = new ArrayList<>();
        for (TransportListItem item : transportItems) {
            if (item.isChecked()) {
                selectedItems.add(item.getId());
            }
        }
        return selectedItems;
    }

    class ItemHolder extends SwappingHolder implements View.OnClickListener {

        private TextView transportName;

        public ItemHolder(View itemView, MultiSelector multiSelector) {
            super(itemView, multiSelector);
            itemView.setOnClickListener(this);
            transportName = (TextView) itemView.findViewById(R.id.tv_transport_name);
        }

        @Override
        public void onClick(View v) {
            TransportListItem item = transportItems.get(getAdapterPosition());
            item.setIsChecked(!item.isChecked());
            multiSelector.tapSelection(this);
            fabApplyTransport.show();
        }
    }
}
