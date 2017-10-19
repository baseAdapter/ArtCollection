package com.tsutsuku.artcollection.ui.shoppingBase;

import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/18
 * @Description Content
 */

public class AddressAdapterEditItem extends BaseAdapterItem<ItemAddress> {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.flAddress)
    FrameLayout flAddress;
    @BindView(R.id.cbCheck)
    CheckBox cbSelect;
    @BindView(R.id.tvDefault)
    TextView tvDefault;
    @BindView(R.id.tvDelete)
    TextView tvDelete;
    @BindView(R.id.ivDelete)
    ImageView ivDelete;
    @BindView(R.id.tvEdit)
    TextView tvEdit;
    @BindView(R.id.ivEdit)
    ImageView ivEdit;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.tvAddress)
    TextView tvAddress;

    private ItemClickListener listener;
    private ItemAddress mItem;

    public AddressAdapterEditItem(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_address_edit;
    }

    @Override
    public void handleData(ItemAddress item, int position) {
        mItem = item;
        tvName.setText(item.getConsigneeName());
        tvMobile.setText(item.getContactNumber());
        tvAddress.setText(item.getProvince() + item.getCity() + item.getCounty() + item.getDetailAddress());
        cbSelect.setChecked("1".equals(item.getIsDefault()));
        cbSelect.setClickable(!"1".equals(item.getIsDefault()));
    }

    @OnClick({R.id.flAddress, R.id.cbCheck, R.id.tvDelete, R.id.ivDelete, R.id.tvEdit, R.id.ivEdit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.flAddress:
            case R.id.tvEdit:
            case R.id.ivEdit: {
                listener.onEditClick(mItem);
            }
            break;
            case R.id.cbCheck: {
                listener.onSelectClick(mItem);
            }
            break;
            case R.id.tvDelete:
            case R.id.ivDelete: {
                listener.onDeleteClick(mItem);
            }
            break;

        }
    }

    public interface ItemClickListener {
        public void onSelectClick(ItemAddress item);

        public void onDeleteClick(ItemAddress item);

        public void onEditClick(ItemAddress item);
    }
}
