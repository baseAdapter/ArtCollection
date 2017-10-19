package com.tsutsuku.artcollection.ui.point;

import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.point.ItemPointRecord;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;

/**
 * @Author Sun Renwei
 * @Create 2017/2/15
 * @Description 积分记录AdapterItem
 */

public class PointRecordAdapterItem extends BaseAdapterItem<ItemPointRecord> {
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
    public void handleData(ItemPointRecord item, int position) {
        tvName.setText(item.getNote());
        tvDate.setText(item.getCreateTime());
        tvPrice.setText(("0".equals(curItem.getIntegrateType()) ? "-" : "+") + item.getIntegrateAmount() + "分");
        tvPrice.setTextColor(context.getResources().getColor("1".equals(curItem.getIntegrateType()) ? R.color.orange : R.color.records_green));
    }
}
