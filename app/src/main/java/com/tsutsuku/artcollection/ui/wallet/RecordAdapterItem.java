package com.tsutsuku.artcollection.ui.wallet;

import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.RecordsInfoBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;

/**
 * @Author Sun Renwei
 * @Create 2017/2/15
 * @Description Content
 */

public class RecordAdapterItem extends BaseAdapterItem<RecordsInfoBean.RecordsBean> {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvPrice)
    TextView tvPrice;

    @Override
    public int getLayoutResId() {
        return R.layout.item_record;
    }

    @Override
    public void handleData(RecordsInfoBean.RecordsBean item, int position) {
        tvName.setText(item.getNote());
        tvDate.setText(item.getBillTime());
        tvPrice.setText(item.getCashAmount());
        tvPrice.setTextColor(context.getResources().getColor(Float.valueOf(item.getCashAmount()) > 0 ? R.color.orange : R.color.records_green));
    }
}
