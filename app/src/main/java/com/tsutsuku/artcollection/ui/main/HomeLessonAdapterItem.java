package com.tsutsuku.artcollection.ui.main;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.lesson.ItemVideo;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.TimeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/5/3
 * @Description
 */

public class HomeLessonAdapterItem extends BaseAdapterItem<ItemVideo> {
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvTeacher)
    TextView tvTeacher;
    @BindView(R.id.tvTime)
    TextView tvTime;

    private OnItemSimpleClickListener<ItemVideo> listener;

    public HomeLessonAdapterItem(OnItemSimpleClickListener<ItemVideo> listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_home_video;
    }

    @Override
    public void handleData(ItemVideo item, int position) {
        Glide.with(context).load(item.getVideoLogo()).into(ivPic);
        tvName.setText(item.getVideoTitle());
        tvTeacher.setText("讲师：" + item.getLecturer());
        tvTime.setText("时长：" + TimeUtils.secondsFormat(item.getVideoLength()));
    }

    @OnClick(R.id.flVideo)
    public void onViewClicked() {
        listener.onItemClick(curItem);
    }
}
