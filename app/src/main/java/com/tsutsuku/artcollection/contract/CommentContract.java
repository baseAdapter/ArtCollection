package com.tsutsuku.artcollection.contract;

import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseAdapter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/17
 * @Description
 */

public class CommentContract {
public interface View{
    void showInputBox(String toUserName);
    void cleanComment();
}

public interface Presenter extends BasePresenter<View>{
    void refresh();
    void comment(String content);
    void resetPId();
    BaseAdapter getAdapter();
}


}