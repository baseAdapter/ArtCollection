package com.tsutsuku.artcollection.contract;

import com.tsutsuku.artcollection.model.LessonBean;
import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Tsutsuku
 * @Create 2017/3/6
 * @Description
 */

public class LessonDetailContract {
    
public interface View{
    void setViews(LessonBean bean);
    void setCollect(boolean isCollect);
}

public interface Presenter extends BasePresenter<View> {
    void share();
}


}