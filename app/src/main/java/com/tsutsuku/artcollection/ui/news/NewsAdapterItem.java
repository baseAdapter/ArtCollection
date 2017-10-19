package com.tsutsuku.artcollection.ui.news;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.ItemNews;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;

/**
 * @Author Sun Renwei
 * @Create 2017/1/11
 * @Description Content
 */

public class NewsAdapterItem extends BaseAdapterItem<ItemNews> {
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvBrief)
    TextView tvBrief;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.flNews)
    FrameLayout flNews;

    @Override
    public int getLayoutResId() {
        return R.layout.item_news;
    }

    @Override
    public void handleData(final ItemNews item, int position) {
        Glide.with(context).load(item.getCoverPhoto()).into(ivPic);
        tvBrief.setText(item.getNewName());
        tvTime.setText(item.getNewTime());
        flNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.launch(context, item.getDetailUrl(), item.getNewName(), item.getCoverPhoto());
            }
        });
    }

}
