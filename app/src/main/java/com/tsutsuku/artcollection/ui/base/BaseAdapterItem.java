package com.tsutsuku.artcollection.ui.base;

import android.content.Context;
import android.view.View;

import com.tsutsuku.artcollection.contract.base.AdapterItem;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2017/1/11
 * @Description Content
 */

public abstract class BaseAdapterItem<T> implements AdapterItem<T>{

    protected Context context;

    protected T curItem;
    protected int curPosition;

    @Override
    public void bindViews(View root) {
        if (context == null){
            context = root.getContext();
        }
        ButterKnife.bind(this, root);
    }

    @Override
    public T getCurItem() {
        return curItem;
    }

    @Override
    public void setCurItem(T item) {
        curItem = item;
    }

    @Override
    public void setCurPosition(int position) {
        curPosition = position;
    }

    @Override
    public void handleData(T item, int position, List<Object> payloads) {
        handleData(item, position);
    }
}
