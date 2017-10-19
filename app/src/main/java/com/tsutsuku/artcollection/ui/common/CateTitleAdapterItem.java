package com.tsutsuku.artcollection.ui.common;

import android.view.View;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.CateBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;

/**
 * @Author Sun Renwei
 * @Create 2017/3/1
 * @Description Content
 */

public class CateTitleAdapterItem extends BaseAdapterItem<CateBean> {

    @BindView(R.id.tvCate)
    TextView tvCate;

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_cate_title;
    }

    @Override
    public void handleData(CateBean item, int position) {
        tvCate.setText(item.getCateName());
    }
}
