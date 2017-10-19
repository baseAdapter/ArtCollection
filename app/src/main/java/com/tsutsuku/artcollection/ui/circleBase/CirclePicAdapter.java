package com.tsutsuku.artcollection.ui.circleBase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.utils.DensityUtils;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2016/10/14
 * @Description content
 */

public class CirclePicAdapter extends RecyclerView.Adapter<CirclePicAdapter.ViewHolder> {

    private Context context;
    private List<String> picList;
    private LayoutInflater inflater;
    private int picHeight;

    public CirclePicAdapter(Context context, List<String> picList) {
        this.context = context;
        this.picList = picList;
        picHeight = (DensityUtils.getDisplayWidth() - DensityUtils.dp2px(120)) / 3;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView ivPic = new ImageView(context);
        ivPic.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, picHeight));
        ivPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ViewHolder(ivPic);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(picList.get(position)).into(holder.ivPic);
    }

    @Override
    public int getItemCount() {
        return picList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView;
        }
    }
}
