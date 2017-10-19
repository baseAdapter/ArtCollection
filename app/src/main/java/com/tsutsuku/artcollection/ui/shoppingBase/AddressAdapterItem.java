package com.tsutsuku.artcollection.ui.shoppingBase;

import android.widget.FrameLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/19
 * @Description Content
 */

public class AddressAdapterItem extends BaseAdapterItem<ItemAddress> {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.flAddress)
    FrameLayout flAddress;
    @BindView(R.id.tvAddress)
    TextView tvAddress;

    private onItemClickListener listener;
    private ItemAddress item;

    public AddressAdapterItem(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_address;
    }

    @Override
    public void handleData(ItemAddress item, int position) {
        this.item = item;
        tvName.setText(item.getConsigneeName());
        tvMobile.setText(item.getContactNumber());
        tvAddress.setText(item.getProvince() + item.getCity() + item.getCounty() + item.getDetailAddress());
    }

    @OnClick(R.id.flAddress)
    public void onClick() {
        listener.onItemClick(item);
    }

    public interface onItemClickListener {
        public void onItemClick(ItemAddress item);
    }
}
