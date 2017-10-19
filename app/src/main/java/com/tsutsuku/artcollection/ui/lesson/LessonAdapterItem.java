package com.tsutsuku.artcollection.ui.lesson;

import android.content.Context;
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
 * @Create 2017/3/5
 * @Description
 */

public class LessonAdapterItem extends BaseAdapterItem<ItemVideo> {
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvTeacher)
    TextView tvTeacher;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvComment)
    TextView tvComment;
    @BindView(R.id.tvSee)
    TextView tvSee;

    private OnItemSimpleClickListener<ItemVideo> listener;

    public LessonAdapterItem(Context context, OnItemSimpleClickListener<ItemVideo> listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_lesson;
    }

    @Override
    public void handleData(ItemVideo item, int position) {
        Glide.with(context).load(item.getVideoLogo()).into(ivPic);
        tvName.setText(item.getVideoTitle());
        tvTeacher.setText("讲师："+item.getLecturer());
        tvTime.setText("时长" + TimeUtils.secondsFormat(item.getVideoLength()));
        tvSee.setText(item.getSeeCount());
        tvComment.setText(item.getCommentCount());
    }

    @OnClick(R.id.flLesson)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
