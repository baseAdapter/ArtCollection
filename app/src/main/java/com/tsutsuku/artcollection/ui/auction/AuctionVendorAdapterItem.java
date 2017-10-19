package com.tsutsuku.artcollection.ui.auction;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.auction.ItemDealVendor;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.ui.shopping.VendorDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/4/3
 * @Description
 */

public class AuctionVendorAdapterItem extends BaseAdapterItem<ItemDealVendor> {
    @BindView(R.id.cbCheck)
    CheckBox cbCheck;
    @BindView(R.id.tvVendor)
    TextView tvVendor;

    private OnItemSimpleClickListener listener;

    public AuctionVendorAdapterItem(OnItemSimpleClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_auction_deal_vendor;
    }

    @Override
    public void handleData(ItemDealVendor item, int position) {
        tvVendor.setText(item.getFarmName());
    }

    @Override
    public void handleData(ItemDealVendor item, int position, List<Object> payloads) {
        switch ((char) payloads.get(0)) {
            case '1': {
                cbCheck.setChecked(item.isChecked());
            }
            break;
        }
    }

    @OnClick({R.id.cbCheck, R.id.flVendor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cbCheck:
                listener.onItemClick(curItem);
                break;
            case R.id.flVendor:
                VendorDetailActivity.launch(context, curItem.getFarmId());
                break;
        }
    }
}
