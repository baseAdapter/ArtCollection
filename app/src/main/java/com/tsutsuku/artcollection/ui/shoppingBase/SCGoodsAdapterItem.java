package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.shopping.ItemGoods;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.TouchDelegateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/2/7
 * @Description
 */

public class SCGoodsAdapterItem extends BaseAdapterItem<ItemGoods> {

    @BindView(R.id.cbCheck)
    CheckBox cbCheck;
    @BindView(R.id.ivGoods)
    ImageView ivGoods;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvNum)
    TextView tvNum;
    @BindView(R.id.flNormal)
    FrameLayout flNormal;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    @BindView(R.id.ivRemove)
    ImageView ivRemove;
    @BindView(R.id.tvEditNum)
    TextView tvEditNum;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    @BindView(R.id.flEdit)
    FrameLayout flEdit;
    @BindView(R.id.flGoods)
    FrameLayout flGoods;

    private onGoodsClickListener listener;

    public SCGoodsAdapterItem(Context context, onGoodsClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        TouchDelegateUtils.expandViewTouchDelegate(cbCheck, 0, 0, DensityUtils.dp2px(20), DensityUtils.dp2px(15));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_goods;
    }

    @Override
    public void handleData(ItemGoods item, int position) {
        cbCheck.setChecked(item.isChecked());
        Glide.with(context).load(item.getProductCover()).into(ivGoods);
        tvName.setText(item.getProductName());
        tvPrice.setText(item.getProductPrice());
        tvNum.setText("X" + item.getProductAmount());
        tvEditNum.setText(item.getProductAmount());
        flNormal.setVisibility(item.isEdit() ? View.GONE : View.VISIBLE);
        flEdit.setVisibility(item.isEdit() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void handleData(ItemGoods item, int position, List<Object> payloads) {
        switch ((char) payloads.get(0)) {
            case '1': {
                cbCheck.setChecked(item.isChecked());
            }
            break;
            case '2': {
                tvNum.setText("X" + item.getProductAmount());
                tvEditNum.setText(item.getProductAmount());
                flNormal.setVisibility(item.isEdit() ? View.GONE : View.VISIBLE);
                flEdit.setVisibility(item.isEdit() ? View.VISIBLE : View.GONE);
            }
            break;
            case '3':{
                tvEditNum.setText(item.getProductAmount());
            }
        }

    }

    @OnClick({R.id.cbCheck, R.id.ivAdd, R.id.ivRemove, R.id.tvEditNum, R.id.btnDelete, R.id.flGoods})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cbCheck:
                listener.onCheckClick(curItem);
                break;
            case R.id.ivAdd:
                listener.add(curItem);
                break;
            case R.id.ivRemove:
                listener.remove(curItem);
                break;
            case R.id.tvEditNum:
                listener.editNum(curItem);
                break;
            case R.id.btnDelete:
                listener.delete(curItem);
                break;
            case R.id.flGoods:
                listener.onGoodsClick(curItem);
                break;
        }
    }

    public interface onGoodsClickListener {
        public void add(ItemGoods item);

        public void remove(ItemGoods item);

        public void delete(ItemGoods item);

        public void onGoodsClick(ItemGoods item);

        public void editNum(ItemGoods item);

        public void onCheckClick(ItemGoods item);
    }
}
