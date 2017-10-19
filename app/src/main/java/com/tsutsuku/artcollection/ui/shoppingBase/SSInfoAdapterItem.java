package com.tsutsuku.artcollection.ui.shoppingBase;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.Arith;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/2/11
 * @Description
 */

public class SSInfoAdapterItem extends BaseAdapterItem<ItemVendor.InfoBean> {
    @BindView(R.id.tvDelivery)
    TextView tvDelivery;
    @BindView(R.id.flDelivery)
    FrameLayout flDelivery;
    @BindView(R.id.etMessage)
    EditText etMessage;
    @BindView(R.id.tvNum)
    TextView tvNum;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvPoint)
    TextView tvPoint;
    @BindView(R.id.sPoint)
    Switch sPoint;

    private SSInfoClick<ItemVendor.InfoBean> listener;

    public SSInfoAdapterItem(SSInfoClick listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_goods_info;
    }

    @Override
    public void handleData(ItemVendor.InfoBean item, int position) {
        tvDelivery.setText(item.getBean().getDes());
        tvPrice.setText(item.getTotalPrice());
        tvNum.setText("共" + item.getTotalNum() + "件商品");
        tvPoint.setText("可用" + item.getVirtualBalance() + "积分抵用" + item.getVirtualBalance() + "元");
        sPoint.setChecked(item.isUsePoint());

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                curItem.setUserNote(s.toString());
            }
        });
    }

    @Override
    public void handleData(ItemVendor.InfoBean item, int position, List<Object> payloads) {
        tvDelivery.setText(item.getBean().getDes());
        tvPrice.setText(Arith.sub(item.getTotalPrice(), item.isUsePoint() ? item.getVirtualBalance() : "0"));
    }

    @OnClick({R.id.flDelivery, R.id.sPoint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.flDelivery: {
                listener.onDeliveryClick(curItem);
            }
            break;
            case R.id.sPoint: {
                curItem.setUsePoint(!curItem.isUsePoint());
                listener.onPointClick(curPosition, (curItem.isUsePoint() ? "-" : "") + curItem.getVirtualBalance());
            }
            break;
        }
    }

    public interface SSInfoClick<T> {
        void onDeliveryClick(T item);

        void onPointClick(int position, String point);
    }
}
