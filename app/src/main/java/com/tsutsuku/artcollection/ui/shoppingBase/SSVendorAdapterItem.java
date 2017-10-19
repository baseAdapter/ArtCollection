package com.tsutsuku.artcollection.ui.shoppingBase;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;

/**
 * @Author Tsutsuku
 * @Create 2017/2/11
 * @Description
 */

public class SSVendorAdapterItem extends BaseAdapterItem<ItemVendor> {
    @BindView(R.id.tvVendor)
    TextView tvVendor;
    @BindView(R.id.llVendor)
    LinearLayout llVendor;
    @BindView(R.id.ivRight)
    ImageView ivRight;

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        ivRight.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_vendor_view;
    }

    @Override
    public void handleData(ItemVendor item, int position) {
        tvVendor.setText(item.getFarmName());
    }


}
