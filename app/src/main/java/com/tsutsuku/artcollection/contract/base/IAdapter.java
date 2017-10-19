package com.tsutsuku.artcollection.contract.base;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/1/11
 * @Description Content
 */

public interface IAdapter<T> {
    void setData(@NonNull List<T> data);

    List<T> getData();

    Object getItemType(T t);

    @Keep
    @NonNull
    AdapterItem createItem(@NonNull Object type);

    @Keep
    @NonNull
    Object getConvertedData(T data, Object type);

    void notifyDataSetChanged();

    int getCurrentPosition();
}
