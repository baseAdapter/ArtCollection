package com.tsutsuku.artcollection.ui.shoppingBase;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.shopping.ItemOrder;
import com.tsutsuku.artcollection.model.shopping.OrderBean;
import com.tsutsuku.artcollection.presenter.shopping.ShoppingRepository;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.Arith;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/14
 * @Description Content
 */

public class OInfoAdapterItem extends BaseAdapterItem<ItemOrder> {
    @BindView(R.id.tvNum)
    TextView tvNum;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.btnPay)
    Button btnPay;
    @BindView(R.id.btnComment)
    Button btnComment;

    @Override
    public int getLayoutResId() {
        return R.layout.item_order_info;
    }

    @Override
    public void handleData(ItemOrder item, int position) {
        resetFunction();
        String num = "0";
        for (ItemOrder.ItemsBean bean : item.getItems()) {
            num = Arith.add(num, bean.getBuyAmount());
        }
        tvNum.setText("共" + num + "件商品");
        tvPrice.setText("¥" + item.getCashFee() + "(含运费￥" + item.getDeliveryFee() + ")");
        setFunction(item);
    }

    @Override
    public void handleData(ItemOrder item, int position, List<Object> payloads) {
        resetFunction();
        setFunction(item);
    }

    @OnClick({R.id.btnCancel, R.id.btnDelete, R.id.btnConfirm, R.id.btnPay, R.id.btnComment})
    public void onClick(View view) {
        ShoppingRepository.orderFunction(context, view.getId(), new OrderBean(curItem.getOrderId(), curItem.getDeliveryStatus(), curItem.getOrderStatus(), curItem.getTotalFee(), curItem.getIsComment()));
    }

    private void resetFunction() {
        btnCancel.setVisibility(View.GONE);
        btnComment.setVisibility(View.GONE);
        btnConfirm.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);
        btnPay.setVisibility(View.GONE);
    }

    private void setFunction(ItemOrder item) {
        switch (item.getOrderStatus()) {
            case "-2":
            case "0":
            case "2": {
                btnDelete.setVisibility(View.VISIBLE);
            }
            break;
            case "-1": {
                btnCancel.setVisibility(View.VISIBLE);
                btnPay.setVisibility(View.VISIBLE);
            }
            break;
            case "1": {
                switch (item.getDeliveryStatus()) {
                    case "0":
                    case "3": {
                        if ("0".equals(item.getIsComment())) {
                            btnComment.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                    case "2": {
                        btnConfirm.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
}
