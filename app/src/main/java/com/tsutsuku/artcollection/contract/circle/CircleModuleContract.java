package com.tsutsuku.artcollection.contract.circle;

import android.content.Context;
import android.os.Bundle;

import com.tsutsuku.artcollection.model.BusBean;
import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/6
 * @Description Content
 */

public class CircleModuleContract {
public interface View{
    void showProgress();
    void hideProgress();
    Context getmContext();
}

public interface Presenter extends BasePresenter<View>{
    void parseIntent(Bundle bundle);
    void replay(String content, int msgId, boolean isComment);
    void loadCircle(boolean refresh);
    BaseRvAdapter getAdapter();
    void parseBusEvent(BusBean msg);
}

public interface Model{
}


}