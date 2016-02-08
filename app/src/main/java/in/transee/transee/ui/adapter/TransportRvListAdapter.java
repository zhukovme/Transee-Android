package in.transee.transee.ui.adapter;

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
import in.transee.transee.model.TransportListItem;

/**
 * @author Michael Zhukov
 */
public class TransportRvListAdapter extends RecyclerView.Adapter<TransportRvListAdapter.ItemHolder> {

    private List<TransportListItem> mTransportItems;
    private FloatingActionButton mFabApplyTransport;
    private MultiSelector mMultiSelector;

    public TransportRvListAdapter(List<TransportListItem> transportItems,
                                  FloatingActionButton fabApplyTransport) {
        mTransportItems = transportItems;
        mMultiSelector = new MultiSelector();
        mMultiSelector.setSelectable(true);
        mFabApplyTransport = fabApplyTransport;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_transport_list, parent, false);
        return new ItemHolder(v, mMultiSelector);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        TransportListItem transportItem = mTransportItems.get(position);
        holder.transportName.setText(transportItem.getName());
    }

    @Override
    public int getItemCount() {
        return mTransportItems.size();
    }

    public List<String> getSelectedItems() {
        List<String> selectedItems = new ArrayList<>();
        for (TransportListItem item : mTransportItems) {
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
            TransportListItem item = mTransportItems.get(getAdapterPosition());
            item.setIsChecked(!item.isChecked());
            mMultiSelector.tapSelection(this);
            mFabApplyTransport.show();
        }
    }
}
