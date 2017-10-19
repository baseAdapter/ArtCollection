package com.tsutsuku.artcollection.other.pawn.ui;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.shopping.ItemGoods;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.other.pawn.model.ItemCrowdType;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.ui.circle.PhotoViewActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingSettleActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/6/26
 * @Description
 */

public class CrowdTypeAdapterItem extends BaseAdapterItem<ItemCrowdType> {
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.llPic)
    LinearLayout llPic;
    @BindView(R.id.tvReturn)
    TextView tvReturn;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvPerson)
    TextView tvPerson;
    @BindView(R.id.btnSupport)
    Button btnSupport;
    @BindView(R.id.tvFee)
    TextView tvFee;

    @Override
    public int getLayoutResId() {
        return R.layout.item_crowd_type;
    }

    @Override
    public void handleData(final ItemCrowdType item, final int position) {
        tvContent.setText(item.getContent());
        tvReturn.setText("预计回报发送时间：项目众筹成功后" + item.getReturn_day() + "天");
        tvPrice.setText("支持" + item.getMinRaised());
        tvFee.setText("配送费用：" + (Float.valueOf(item.getFreight()) == 0 ? "免运费" : ("¥" + item.getFreight())));

        SpannableStringBuilder builder = new SpannableStringBuilder("已有支持人" + item.getHaveNum());
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.c)), 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("/限额" + item.getTotalNum() + "位");
        tvPerson.setText(builder);

        if (item.getHaveNum().equals(item.getTotalNum())) {
            btnSupport.setText("已满额");
            btnSupport.setBackgroundResource(R.drawable.btn_gray);
        } else {
            btnSupport.setText("去支持");
            btnSupport.setBackgroundResource(R.drawable.btn_orange);
        }

        llPic.removeAllViews();
        if (item.getBriefPics().size() == 0){
            llPic.setVisibility(View.GONE);
        } else {
            llPic.setVisibility(View.VISIBLE);
            for (int i = 0; i < item.getBriefPics().size(); i++) {
                ImageView view = new ImageView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtils.dp2px(60), ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.leftMargin = DensityUtils.dp2px(5);
                view.setLayoutParams(layoutParams);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoViewActivity.launchTypeView(context, position, (ArrayList) item.getBriefPics());
                    }
                });
                Glide.with(context).load(item.getBriefPics().get(i)).into(view);
                llPic.addView(view);
            }
        }


    }

    @OnClick(R.id.btnSupport)
    public void onViewClicked() {
        final ItemVendor vendor = new ItemVendor();
        vendor.setFarmId("0");
        vendor.setFarmName("平台");
        final ItemGoods goods = new ItemGoods();
        goods.setProductAmount("1");
        goods.setProductName("档位金额： ¥" + curItem.getMinRaised());
        if (curItem.getBriefPics().size() > 0){
            goods.setProductCover(curItem.getBriefPics().get(0));
        } else {
            goods.setProductCover("");
        }
        goods.setProductId(curItem.getId());
        goods.setProductPrice(curItem.getMinRaised());
        vendor.setProducts(new ArrayList<ItemGoods>() {{
            add(goods);
        }});
        ShoppingSettleActivity.launch(context, "6", new ArrayList<ItemVendor>() {{
            add(vendor);
        }});
    }
}
