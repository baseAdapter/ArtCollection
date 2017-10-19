package com.tsutsuku.artcollection.presenter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/3
 * @Description BasePresenter 基础类
 */

public interface BasePresenter<V> {
    void attachView(V view);

    void detachView();
}
