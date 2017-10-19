package com.tsutsuku.artcollection.contract;

import android.support.annotation.Nullable;

import com.tsutsuku.artcollection.model.IdentifyInfoBean;
import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/18
 * @Description Content
 */

public class IdentifyInfoContract {
    
public interface View{
    void setData(IdentifyInfoBean bean);
    void hideDialog(IdentifyInfoBean bean);
}

public interface Presenter extends BasePresenter<View>{
    void identify(String year, String money, String meno);
}

}