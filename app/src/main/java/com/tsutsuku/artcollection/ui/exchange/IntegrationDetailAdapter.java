package com.tsutsuku.artcollection.ui.exchange;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/28.
 */

public class IntegrationDetailAdapter extends RecyclerView.Adapter<IntegrationDetailAdapter.ViewHolder>{
    private Context mContext;
    private List<IntegrationDetail> mData;
    private LayoutInflater mInflater;

    public IntegrationDetailAdapter(Context context, List<IntegrationDetail> data) {
        if (data == null) {
            mData = new ArrayList<>();
        }else {
            mData = data;
        }
        mInflater = LayoutInflater.from(context);
        mContext = context;

    }

    public void update(List<IntegrationDetail> data) {
        if (data != null) {
            mData = data;
            this.notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_coin_detail,parent,false) ;
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mData.size() > 0 && mData != null) {
            IntegrationDetail detail = mData.get(position);
            holder.numberTv.setText("+" + detail.getIntegrateAmount() + "  分");
            holder.numberTv.setTextColor(Color.parseColor("#EA8C28"));
            holder.timeTv.setText(detail.getCreateTime());
            holder.wayTv.setText(detail.getNote());
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView numberTv;
        private TextView wayTv;
        private TextView timeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            numberTv = (TextView) itemView.findViewById(R.id.detail_numberTv);
            wayTv = (TextView) itemView.findViewById(R.id.detail_wayTv);
            timeTv = (TextView) itemView.findViewById(R.id.detail_timeTv);
        }
    }
}
