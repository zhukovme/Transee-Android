package in.transee.transee.ui.main.map;

import android.content.Context;
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
import in.transee.transee.injection.ActivityContext;
import in.transee.transee.util.TimeUtil;

/**
 * @author Michael Zhukov
 */
public class TransportInfoRvAdapter
        extends RecyclerView.Adapter<TransportInfoRvAdapter.TransportInfoVH> {

    private final Context context;
    private List<TransportInfo> transportInfoList;

    @Inject
    public TransportInfoRvAdapter(@ActivityContext Context context) {
        this.context = context;
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

        String timeDif = TimeUtil.getMinuteDiff(context, transportInfo.getTime());
        holder.tvTime.setText(transportInfo.getTime());
        holder.rvStation.setText(transportInfo.getStation());
        holder.tvTimeDifference.setText(timeDif);
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
