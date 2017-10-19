package com.tsutsuku.artcollection.ui.wallet;

import android.widget.ImageView;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.wallet.ItemPayType;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/15
 * @Description Content
 */

public class PayTypeAdapterItem extends BaseAdapterItem<ItemPayType> {
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvName)
    TextView tvName;

    private OnItemSimpleClickListener listener;

    public PayTypeAdapterItem(OnItemSimpleClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_pay_type;
    }

    @Override
    public void handleData(ItemPayType item, int position) {
        ivPic.setImageResource(item.getPicResId());
        tvName.setText(item.getName());
    }

    @OnClick(R.id.flBase)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
