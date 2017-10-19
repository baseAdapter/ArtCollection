package com.tsutsuku.artcollection.other.pawn.ui;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.other.pawn.model.ItemCrowd;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.Arith;
import com.tsutsuku.artcollection.utils.TimeUtils;
import com.tsutsuku.artcollection.utils.glideTransform.GlideRoundTransform;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/6/22
 * @Description
 */

public class CrowdAdapterItem extends BaseAdapterItem<ItemCrowd> {
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.sbBase)
    SeekBar sbBase;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.tvPerson)
    TextView tvPerson;
    @BindView(R.id.tvCash)
    TextView tvCash;
    @BindView(R.id.tvTime)
    TextView tvTime;

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        sbBase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_crowd;
    }

    @Override
    public void handleData(ItemCrowd item, int position) {
        Glide.with(context).load(item.getLogo()).transform(new GlideRoundTransform(context)).into(ivPic);

        tvTime.setText(TimeUtils.getCrowTime(item.getEndIntTime()));
        tvPerson.setText(item.getHaveNum()+"人");
        tvCash.setText(item.getHaveRaised()+"元");

        sbBase.setMax(100);
        int progress = Integer.parseInt(Arith.div(Arith.mul(item.getHaveRaised(), "100"), item.getTotalRaised()));
        if (progress < 100){
            sbBase.setProgress(progress);
            tvProgress.setText(progress + "%");
        } else {
            sbBase.setProgress(100);
            tvProgress.setText(100 + "%");
        }


        switch (item.getStatus()) {
            case "0": {
                tvStatus.setText("预展中");
                tvStatus.setBackgroundResource(R.drawable.btn_gray);
            }
            break;
            case "1": {
                tvStatus.setText("众筹中");
                tvStatus.setBackgroundResource(R.drawable.btn_orange);
            }
            break;
            case "2": {
                tvStatus.setText("众筹成功");
                tvStatus.setBackgroundResource(R.drawable.btn_orange);
            }
            break;
            case "3": {
                tvStatus.setText("众筹失败");
                tvStatus.setBackgroundResource(R.drawable.btn_orange);
            }
            break;
        }
    }

    @OnClick(R.id.llBase)
    public void onViewClicked() {
        CrowdDetailActivity.launch(context, curItem.getId());
    }
}
