package com.tsutsuku.artcollection.ui.exchange;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.ExchangeBean;
import com.tsutsuku.artcollection.ui.point.MinePointActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingAddressActivity;
import com.tsutsuku.artcollection.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */

public class ExchangeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public static final String TAG = ExchangeAdapter.class.getSimpleName();

    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1;
    private Context context;
    private List<ExchangeBean> mData;

    private List<String> imgs;


    public ExchangeAdapter(Context context) {
        this.context = context;
        imgs = new ArrayList<>();
    }

    public void setData(List<ExchangeBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    /**
     * @param imgs 添加头布局
     **/
    public void addHeadView(List<String> imgs) {
        this.imgs = imgs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //  暂时依照data == null来判定添加头布局，根据需求改
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                View head_view = LayoutInflater.from(context).inflate(R.layout.layout_head_exchange, parent, false);
                final HeaderViewHolder headViewHolder = new HeaderViewHolder(head_view);
                headViewHolder.goldCoinExchangeLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MinePointActivity.class);
                        context.startActivity(intent);
                    }
                });
                headViewHolder.recordExchangeLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, ExchangeRecordActivity.class));
                    }
                });
                headViewHolder.addressExchangeLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, ShoppingAddressActivity.class));
                    }
                });
                return headViewHolder;
            case TYPE_ITEM:
                final View view = LayoutInflater.from(context).inflate(R.layout.item_exchange_recy, parent, false);
                ViewHolder holder = new ViewHolder(view);
                return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:

                HeaderViewHolder headViewHolder = (HeaderViewHolder) holder;
                headViewHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                //设置图片加载器
                headViewHolder.banner.setImageLoader(new GlideImageLoader());

                //设置图片集合
                headViewHolder.banner.setImages(imgs);
                headViewHolder.banner.isAutoPlay(true);
                //设置轮播时间
                headViewHolder.banner.setDelayTime(3000);
                //设置指示器位置（当banner模式中有指示器时）
                headViewHolder.banner.setIndicatorGravity(BannerConfig.CENTER);
                //设置banner动画效果
                headViewHolder.banner.setBannerAnimation(Transformer.BackgroundToForeground);
                //banner设置方法全部调用完毕时最后调用
                headViewHolder.banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                    }
                });
                headViewHolder.banner.start();
                break;
            case TYPE_ITEM:
                ViewHolder itemHolder = (ViewHolder) holder;
                itemHolder.itemView.setTag(position-1);
                ExchangeBean data = mData.get(position-1);
                itemHolder.priceText.setText(data.getNeed_gold() + "金币");
                itemHolder.nameText.setText(data.getName());
                Glide.with(context).load(data.getCoverPhoto()).into(itemHolder.itemImageView);
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (mData != null && mData.size() > 0) {
            return mData.size() + 1;
        } else {
            return 1;
        }
    }




    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout goldCoinExchangeLay, recordExchangeLay, addressExchangeLay;
        public Banner banner;

        public HeaderViewHolder(View view) {
            super(view);
            goldCoinExchangeLay = (LinearLayout) view.findViewById(R.id.goldCoinExchangeLay);
            recordExchangeLay = (LinearLayout) view.findViewById(R.id.recordExchangeLay);
            addressExchangeLay = (LinearLayout) view.findViewById(R.id.addressExchangeLay);
            banner = (Banner) view.findViewById(R.id.banner_exchangeMall);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
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
