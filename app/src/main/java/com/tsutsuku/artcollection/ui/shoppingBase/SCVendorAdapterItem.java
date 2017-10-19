package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.TouchDelegateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @Author Sun Renwei
 * @Create 2017/2/6
 * @Description Content
 */

public class SCVendorAdapterItem extends BaseAdapterItem<ItemVendor> {


    @BindView(R.id.tvVendor)
    TextView tvVendor;
    @BindView(R.id.tvEdit)
    TextView tvEdit;
    @BindView(R.id.flVendor)
    FrameLayout flVendor;
    @BindView(R.id.cbCheck)
    CheckBox cbCheck;

    private onVendorClickListener listener;

    public SCVendorAdapterItem(Context context, onVendorClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        TouchDelegateUtils.expandViewTouchDelegate(cbCheck, 0, 0, DensityUtils.dp2px(20), DensityUtils.dp2px(15));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_vendor_goods;
    }

    @Override
    public void handleData(ItemVendor item, int position) {
        cbCheck.setChecked(item.isChecked());
        tvVendor.setText(item.getFarmName());
        tvEdit.setText(item.isEdit() ? R.string.finish : R.string.edit);
    }

    @Override
    public void handleData(ItemVendor item, int position, List<Object> payloads) {
        switch ((char) payloads.get(0)) {
            case '1': {
                cbCheck.setChecked(item.isChecked());
            }
            break;
            case '2': {
                tvEdit.setText(item.isEdit() ? R.string.finish : R.string.edit);
            }
            break;
        }
    }

    @OnClick({R.id.tvEdit, R.id.flVendor, R.id.cbCheck})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvEdit:
                listener.onEditClick(curItem);
                break;
            case R.id.flVendor:
                listener.onVendorClick(curItem);
                break;
            case R.id.cbCheck:
                listener.onCheckClick(curItem);
                break;
        }
    }

    public interface onVendorClickListener {
        public void onVendorClick(ItemVendor item);

        public void onEditClick(ItemVendor item);

        public void onCheckClick(ItemVendor item);
    }
}
