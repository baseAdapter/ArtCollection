package com.tsutsuku.artcollection.other.custom.ui;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.other.custom.model.ItemCustomBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/6/8
 * @Description
 */

public class CustomAdapterItem extends BaseAdapterItem<ItemCustomBean> {
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAuthor)
    TextView tvAuthor;

    @Override
    public int getLayoutResId() {
        return R.layout.item_custom;
    }

    @Override
    public void handleData(ItemCustomBean item, int position) {
        Glide.with(context).load(item.getLogo()).into(ivPic);
        tvName.setText(item.getTitle());
        SpannableStringBuilder builder = new SpannableStringBuilder("大师：");
        builder.append(item.getAuthor());
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orange)), 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("|");
        builder.append(item.getAuthorKeyWord());
        tvAuthor.setText(builder);

    }

    @OnClick(R.id.flBase)
    public void onViewClicked() {
        CustomDetailActivity.launch(context, curItem.getDiyId());
    }
}
