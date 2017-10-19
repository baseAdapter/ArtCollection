package com.tsutsuku.artcollection.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tsutsuku.artcollection.utils.DensityUtils;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/7/9
 * @Description
 */

public class SelectAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public SelectAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(40));
            TextView tvSelect = new TextView(context);
            tvSelect.setLayoutParams(layoutParams);
            tvSelect.setTextSize(18);
            tvSelect.setGravity(Gravity.CENTER);
            tvSelect.setText(list.get(position));
            convertView = tvSelect;
        }
        return convertView;
    }
}
