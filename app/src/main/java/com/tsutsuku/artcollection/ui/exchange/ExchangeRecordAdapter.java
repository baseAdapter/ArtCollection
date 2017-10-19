package com.tsutsuku.artcollection.ui.exchange;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/8.
 */

public class ExchangeRecordAdapter extends RecyclerView.Adapter<ExchangeRecordAdapter.ViewHolder> {
    public static final String BASE_ICON_URL = "http://yssc.51urmaker.com";

    private Context mContext;
    private List<ExchangeRecord> mData;
    private LayoutInflater mInflater;

    public ExchangeRecordAdapter(Context context, List<ExchangeRecord> data) {
        if (data == null){
            mData = new ArrayList<>();
        }else {
            mData = data;
        }
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public void update(List<ExchangeRecord> data) {
        if (data != null) {
            mData = data;
            this.notifyDataSetChanged();
        }

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_exchange_record,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mData != null && mData.size() > 0) {
            ExchangeRecord record = mData.get(position);
            holder.goldNumRecord.setText(record.getGoldAmount()+"金币");
            holder.nameRecord.setText(record.getName());
            holder.numberRecord.setText("x  "+record.getOutOrderId());
            holder.timeRecord.setText(record.getCreateTime());

            Glide.with(mContext).load(BASE_ICON_URL+record.getCoverPhoto()).into(holder.iconUrl);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iconUrl;
        private TextView nameRecord;
        private TextView timeRecord;
        private TextView goldNumRecord;
        private TextView numberRecord;

        public ViewHolder(View itemView) {
            super(itemView);
            iconUrl = (ImageView) itemView.findViewById(R.id.record_icon);
            nameRecord = (TextView) itemView.findViewById(R.id.record_nameTv);
            timeRecord = (TextView) itemView.findViewById(R.id.record_timeTv);
            goldNumRecord = (TextView) itemView.findViewById(R.id.record_goldTv);
            numberRecord = (TextView) itemView.findViewById(R.id.record_numTv);

        }
    }
}
