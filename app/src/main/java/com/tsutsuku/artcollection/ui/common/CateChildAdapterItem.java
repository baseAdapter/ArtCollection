package com.tsutsuku.artcollection.ui.common;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.CateBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/3/1
 * @Description Content
 */

public class CateChildAdapterItem extends BaseAdapterItem<CateBean.ChildBean> {
    @BindView(R.id.flCate)
    FrameLayout flCate;
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvName)
    TextView tvName;

    private OnItemSimpleClickListener listener;

    public CateChildAdapterItem(OnItemSimpleClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_cate_child;
    }

    @Override
    public void handleData(CateBean.ChildBean item, int position) {
        Glide.with(context).load(item.getPhoto()).error(R.drawable.default_small_pic).into(ivPic);
        tvName.setText(item.getCateName());
    }

    @OnClick(R.id.flCate)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
