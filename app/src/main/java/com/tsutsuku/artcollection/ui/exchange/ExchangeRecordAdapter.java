package com.tsutsuku.artcollection.ui.exchange;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.view.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/8.
 */

public class ExchangeRecordAdapter extends RecyclerView.Adapter<ExchangeRecordAdapter.ViewHolder> {
    public static final String BASE_ICON_URL = "http://yssc.51urmaker.com";

    private Context context;
    private List<ExchangeRecord> mData;
    private OnDeleteListener listener;

    public ExchangeRecordAdapter(ExchangeRecordActivity context, List<ExchangeRecord> data) {
        if (data == null) {
            mData = new ArrayList<>();
        } else {
            mData = data;
        }
        this.context = context;
        this.listener = context;
    }


    public void update(List<ExchangeRecord> data) {
        if (data != null) {
            mData = data;
            this.notifyDataSetChanged();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_exchange_record_delete, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ExchangeRecord record = mData.get(position);
        holder.goldNumRecord.setText(record.getGoldAmount() + "金币");
        holder.nameRecord.setText(record.getName());
        holder.numberRecord.setText("x  " + record.getNums());
        holder.timeRecord.setText(record.getCreateTime());
        holder.itemView.setTag(position);
        Glide.with(context).load(BASE_ICON_URL + record.getCoverPhoto()).into(holder.iconUrl);
        final View finalConvertView = holder.itemView;
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.delete(Long.parseLong(record.getOrderId()));
                ((SwipeMenuLayout) finalConvertView).quickClose();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconUrl;
        private TextView nameRecord, delete;
        private TextView timeRecord;
        private TextView goldNumRecord;
        //        public CheckBox cb_edit;
        private TextView numberRecord;
        public RelativeLayout ll_item;

        public ViewHolder(View itemView) {
            super(itemView);
            iconUrl = (ImageView) itemView.findViewById(R.id.record_icon);
            nameRecord = (TextView) itemView.findViewById(R.id.record_nameTv);
            delete = (TextView) itemView.findViewById(R.id.delete);
            timeRecord = (TextView) itemView.findViewById(R.id.record_timeTv);
            goldNumRecord = (TextView) itemView.findViewById(R.id.record_goldTv);
            numberRecord = (TextView) itemView.findViewById(R.id.record_numTv);
//            cb_edit = (CheckBox) itemView.findViewById(R.id.cb_edit);
            ll_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);

        }
    }

    public interface OnDeleteListener {
        void delete(long id);
    }
}
