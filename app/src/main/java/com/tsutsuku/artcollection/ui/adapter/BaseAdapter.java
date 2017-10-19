//package com.tsutsuku.artcollection.ui.adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.List;
//
///**
// * @Author Sun Renwei
// * @Create 2017/1/6
// * @Description Content
// */
//
//public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
//    protected LayoutInflater inflater;
//    protected List<Object> list;
//
//
//    protected abstract int getLayoutResId(int viewType);
//
//    @Override
//    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = inflater.inflate(getLayoutResId(viewType), parent, false);
//        return new VH(view);
//    }
//
//    @Override
//    public int getItemCount() {
//        return list == null ? 0 : list.size();
//    }
//
//
/////}
