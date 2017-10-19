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
 * Created by Administrator on 2017/10/8.
 */

public class CoinDetailAdapter extends RecyclerView.Adapter<CoinDetailAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CoinDetail> mData;

    public CoinDetailAdapter(Context context, List<CoinDetail> data) {
        if (data == null) {
            mData = new ArrayList<>();
        }else {
            mData = data;
        }
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }


    public void update(List<CoinDetail> data) {
        if (data != null) {
            mData = data;
            this.notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_coin_detail,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mData != null && mData.size() > 0) {
            CoinDetail coinDetail = mData.get(position);
            holder.numberTv.setText("+"+coinDetail.getGoldNums() + "  枚");
            holder.numberTv.setTextColor(Color.parseColor("#EA8C28"));
            holder.timeTv.setText(coinDetail.getCreateTime());
            holder.wayTv.setText(coinDetail.getNote());
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
