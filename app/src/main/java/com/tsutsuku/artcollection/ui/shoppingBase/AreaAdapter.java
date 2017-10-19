package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.utils.DensityUtils;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/1/19
 * @Description Content
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
    private List<String> list;
    private Context context;
    private onItemClickListener listener;

    public AreaAdapter(List<String> list, Context context, onItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    public List<String> getData() {
        return list;
    }

    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView tvArea = new TextView(context);
        return new ViewHolder(tvArea);
    }

    @Override
    public void onBindViewHolder(AreaAdapter.ViewHolder holder, int position) {
        holder.curPosition = position;
        holder.tvArea.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvArea;
        int curPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            tvArea = (TextView) itemView;
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(40));
            tvArea.setGravity(Gravity.CENTER_VERTICAL);
            tvArea.setPadding(DensityUtils.dp2px(15), 0, 0, 0);
            tvArea.setLayoutParams(layoutParams);
            tvArea.setBackgroundResource(R.drawable.bg_selector);
            tvArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(curPosition);
                }
            });
        }
    }

    public interface onItemClickListener {
        public void onItemClick(int position);
    }
}