package com.tsutsuku.artcollection.ui.collection;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemClickListener;
import com.tsutsuku.artcollection.model.collection.ItemCollection;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/3/25
 * @Description
 */

public class CollectionAdapterItem extends BaseAdapterItem<ItemCollection> {
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvBrief)
    TextView tvBrief;
    @BindView(R.id.flBase)
    FrameLayout flBase;


    private OnItemClickListener listener;

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        flBase.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(curItem);
                return true;
            }
        });
    }

    public CollectionAdapterItem(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_collection;
    }

    @Override
    public void handleData(ItemCollection item, int position) {
        Glide.with(context).load(item.getPic()).into(ivPic);
        tvName.setText(item.getTitle());
        tvBrief.setText(item.getBrief());
    }

    @OnClick(R.id.flBase)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
