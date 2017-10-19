package com.tsutsuku.artcollection.ui.exchange;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.ExchangeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */

public class ExchangeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    public static final String TAG = ExchangeAdapter.class.getSimpleName();

    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1;
    private Context mContext;
    private List<ExchangeBean> mData;
    private LayoutInflater mInflate;

    private View headView;
    private int headViewSize = 0;

    private boolean isAddHead=false;
    private OnItemClickListener mOnItemClickListener = null;


    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view,int position);
    }

    public ExchangeAdapter(Context context, List<ExchangeBean> data) {
        if (data == null) {
            mData = new ArrayList<>();
        }else {
            mData = data;
        }
        mInflate = LayoutInflater.from(context);
        mContext = context;
    }

    /**
     *
     *@param view
     *添加头布局
     *
     **/
    public void addHeadView(View view) {
        mData.add(0,null);
        headViewSize = 1;
        isAddHead = true;
    }

    @Override
    public int getItemViewType(int position) {

        //  暂时依照data == null来判定添加头布局，根据需求改
        if (mData.get(position) == null) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public void update(List<ExchangeBean> data) {
        if (data != null) {
            mData = data;
            this.notifyDataSetChanged();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_HEADER:
                holder = new HeaderViewHolder(mInflate.inflate(R.layout.layout_head_exchange, parent, false));
                break;
            case TYPE_ITEM:
                View inflate = mInflate.inflate(R.layout.item_exchange_recy, parent, false);
                holder = new ViewHolder(inflate);
                inflate.setOnClickListener(this);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_ITEM:
                ViewHolder itemHolder = (ViewHolder) holder;
                itemHolder.itemView.setTag(position);
                ExchangeBean data = mData.get(position);
                itemHolder.priceText.setText(data.getNeed_gold()+"金币");
                itemHolder.nameText.setText(data.getName());
                Glide.with(mContext).load(data.getCoverPhoto()).into(itemHolder.itemImageView);
                break;
        }
      
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    /**
     *
     *最后暴露给外面的调用者，定义一个设置Listener的方法()
     *@param listener
     *
     **/
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView itemImageView;
        private TextView nameText;
        private TextView priceText;


        public ViewHolder(View itemView) {
            super(itemView);
            itemImageView = (ImageView) itemView.findViewById(R.id.item_exchange_img);
            nameText = (TextView) itemView.findViewById(R.id.item_exchange_name);
            priceText = (TextView) itemView.findViewById(R.id.item_exchange_price);
        }
    }



}
