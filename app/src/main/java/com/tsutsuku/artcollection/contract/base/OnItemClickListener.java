package com.tsutsuku.artcollection.contract.base;

/**
 * @Author Sun Renwei
 * @Create 2017/1/6
 * @Description Content
 */

public interface OnItemClickListener<T> {
    void onItemClick(T item);

    void onItemLongClick(T item);
}
