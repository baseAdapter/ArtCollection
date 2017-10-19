package com.tsutsuku.artcollection.ui.common;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.CateBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/3/1
 * @Description Content
 */

public class CateMenuAdapterItem extends BaseAdapterItem<CateBean> {
    @BindView(R.id.tvCate)
    TextView tvCate;
    @BindView(R.id.flCate)
    FrameLayout flCate;
    @BindView(R.id.vSelect)
    View vSelect;

    private OnItemSimpleClickListener listener;

    public CateMenuAdapterItem(OnItemSimpleClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_cate_menu;
    }

    @Override
    public void handleData(CateBean item, int position) {
        tvCate.setText(item.getCateName());
        flCate.setBackgroundResource(item.isCheck() ? R.color.white : R.color.bg);
        vSelect.setVisibility(item.isCheck() ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.flCate)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
