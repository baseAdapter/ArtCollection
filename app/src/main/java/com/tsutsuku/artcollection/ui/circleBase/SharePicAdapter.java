package com.tsutsuku.artcollection.ui.circleBase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.utils.DensityUtils;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2016/10/15
 * @Description 分享图片适配器
 */

public class SharePicAdapter extends RecyclerView.Adapter<SharePicAdapter.ViewHolder> {
    private static final int TYPE_ADD = 1;
    private static final int TYPE_ITEM = 0;

    private static int MAX_PHOTO_NUM = Constants.MAX_SHARE_PIC;

    private Context context;
    private List<String> picList;
    private LayoutInflater inflater;

    public void showSelectDialog() {
    }

    public void showPicView(int postion) {
    }

    public SharePicAdapter(Context context, List<String> picList) {
        this.context = context;
        this.picList = picList;
        inflater = LayoutInflater.from(context);
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
        View view = new ImageView(parent.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(65));
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_ADD: {
                holder.ivPic.setImageResource(R.drawable.icon_add_picture);
                holder.ivPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSelectDialog();
                    }
                });
            }
            break;
            case TYPE_ITEM: {
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
        if (picList.size() < MAX_PHOTO_NUM){
            return picList.size() + 1;
        } else {
            return MAX_PHOTO_NUM;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView;
        }
    }


}
