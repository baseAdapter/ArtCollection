package com.tsutsuku.artcollection.ui.main;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;

/**
 * @Author Sun Renwei
 * @Create 2017/1/24
 * @Description Content
 */

public class HomeMenuAdapterItem extends BaseAdapterItem<HomeBean.MenusBean> {

    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvMenu)
    TextView tvMenu;
    @BindView(R.id.flMenu)
    FrameLayout flMenu;

    private OnItemSimpleClickListener listener;
    public HomeMenuAdapterItem(Context context, OnItemSimpleClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        flMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(curItem);
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_home_menu;
    }

    @Override
    public void handleData(HomeBean.MenusBean item, int position) {
        curItem = item;
        Glide.with(context).load(item.getPic()).into(ivPic);
        tvMenu.setText(item.getName());
    }

}
