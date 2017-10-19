package com.tsutsuku.artcollection.other.pawn.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2016/10/15
 * @Description 典当发布图片适配器
 */

public class PublishPicAdapter extends RecyclerView.Adapter<PublishPicAdapter.ViewHolder> {
    private static final int TYPE_ADD = 1;
    private static final int TYPE_ITEM = 0;

    private static int MAX_PHOTO_NUM = 6;

    private Context context;
    private List<String> picList;

    public void showSelectDialog() {
    }

    public void showPicView(int position) {
    }

    public PublishPicAdapter(Context context) {
        this.context = context;
        picList = new ArrayList<>();
    }

    public List<String> getData() {
        return picList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == picList.size()) {
            return TYPE_ADD;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pawn_pic_add, null);
        GridLayoutManager.LayoutParams layoutParams = new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (DensityUtils.getDisplayWidth() - DensityUtils.dp2px(48)) / 3);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_ADD: {
                holder.flAdd.setVisibility(View.VISIBLE);
                holder.ivPic.setVisibility(View.GONE);
                holder.flAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSelectDialog();
                    }
                });
            }
            break;
            case TYPE_ITEM: {
                holder.flAdd.setVisibility(View.GONE);
                holder.ivPic.setVisibility(View.VISIBLE);
                Glide.with(context).load("file://" + picList.get(position)).centerCrop().into(holder.ivPic);
                holder.ivPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPicView(position);
                    }
                });
            }
            break;
        }
    }

    @Override
    public int getItemCount() {
        if (picList.size() < MAX_PHOTO_NUM) {
            return picList.size() + 1;
        } else {
            return MAX_PHOTO_NUM;
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        FrameLayout flAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.ivPic);
            flAdd = (FrameLayout) itemView.findViewById(R.id.flAdd);
        }
    }
}
