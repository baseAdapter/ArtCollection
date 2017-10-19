package com.tsutsuku.artcollection.contract;

import com.tsutsuku.artcollection.model.ActivityInfo;
import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Tsutsuku
 * @Create 2017/3/18
 * @Description 活动详情
 */

public class ActivityDetailContract {
    
public interface View{
    void setViews(ActivityInfo info);
    void setLiveStatus(int status);
    void setFollow(boolean follow);
    void showPayDialog();
    void showStreamDialog();
}

public interface Presenter extends BasePresenter<View> {
    void apply(String payType);
    void openLive();
    void follow();
    void share();
    void chat();
    void stream(boolean needRecord);
}



}