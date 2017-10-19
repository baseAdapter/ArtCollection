package com.tsutsuku.artcollection.ui.search;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.ItemSearch;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/4/3
 * @Description
 */

public class SearchAdapterItem extends BaseAdapterItem<ItemSearch> {
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvBrief)
    TextView tvBrief;

    private OnItemSimpleClickListener listener;

    public SearchAdapterItem(OnItemSimpleClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_search;
    }

    @Override
    public void handleData(ItemSearch item, int position) {
        if ("2".equals(item.getType())) {
            tvName.setText(item.getProductName());
        } else {
            tvName.setText(item.getName());
        }

        Glide.with(context).load(item.getPic()).into(ivPic);
        tvBrief.setText(item.getBrief());
    }

    @OnClick(R.id.cvSearch)
    public void onViewClicked() {
        listener.onItemClick(curItem);
    }
}
