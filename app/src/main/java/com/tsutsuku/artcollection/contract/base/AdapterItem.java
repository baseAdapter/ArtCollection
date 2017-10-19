package com.tsutsuku.artcollection.contract.base;

import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/1/11
 * @Description Content
 */

public interface AdapterItem<T> {

    @LayoutRes
    int getLayoutResId();

    //绑定控件
    void bindViews(final View root);

    //刷新数据
    void handleData(T item, int position);

    void handleData(T item, int position, List<Object> payloads);

    T getCurItem();

    void setCurItem(T item);

    void setCurPosition(int position);


}
