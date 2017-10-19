package com.tsutsuku.artcollection.ui.circle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.ItemExport;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

import butterknife.BindView;

/**
 * @Author Sun Renwei
 * @Create 2017/2/28
 * @Description Content
 */

public class ExportAdapterItem extends BaseAdapterItem<ItemExport> {
    @BindView(R.id.ivExpert)
    ImageView ivExpert;
    @BindView(R.id.tvExpert)
    TextView tvExpert;
    @BindView(R.id.tvExpertTitle)
    TextView tvExpertTitle;
    @BindView(R.id.tvFee)
    TextView tvFee;
    @BindView(R.id.flExpert)
    FrameLayout flExpert;

    OnItemSimpleClickListener<ItemExport> listener;

    public ExportAdapterItem(OnItemSimpleClickListener<ItemExport> listener) {
        this.listener = listener;
    }

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        flExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(curItem);
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_export;
    }

    @Override
    public void handleData(ItemExport item, int position) {
        Glide.with(context).load(item.getPhoto()).transform(new CircleTransform(context)).into(ivExpert);
        tvExpert.setText(item.getNickname());
        tvExpertTitle.setText(item.getPersonalSign());
        tvFee.setText("¥" + item.getUseMoney());
    }
}
