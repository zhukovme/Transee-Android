package in.transee.transee.ui.main.map;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.transee.transee.R;
import in.transee.transee.data.transport.info.TransportInfo;

/**
 * @author Michael Zhukov
 */
public class TransportInfoRvAdapter
        extends RecyclerView.Adapter<TransportInfoRvAdapter.TransportInfoVH> {

    private List<TransportInfo> transportInfoList;

    @Inject
    public TransportInfoRvAdapter() {
        this.transportInfoList = new ArrayList<>();
    }

    public void setData(List<TransportInfo> transportInfoList) {
        this.transportInfoList = transportInfoList;
        notifyDataSetChanged();
    }

    public void clear() {
        this.transportInfoList.clear();
        notifyDataSetChanged();
    }

    @Override
    public TransportInfoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_transport_info, parent, false);
        return new TransportInfoVH(v);
    }

    @Override
    public void onBindViewHolder(TransportInfoVH holder, int position) {
        TransportInfo transportInfo = transportInfoList.get(position);

        holder.tvTime.setText("Скоро");
        holder.rvStation.setText(transportInfo.getTime());
        holder.tvTimeDifference.setText(transportInfo.getStation());
    }

    @Override
    public int getItemCount() {
        return transportInfoList.size();
    }

    class TransportInfoVH extends RecyclerView.ViewHolder {

        private TextView tvTime;
        private TextView rvStation;
        private TextView tvTimeDifference;

        public TransportInfoVH(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_transport_info_time);
            rvStation = (TextView) itemView.findViewById(R.id.tv_transport_info_station);
            tvTimeDifference = (TextView) itemView.findViewById(R.id.tv_transport_info_dif_time);
        }
    }
}
