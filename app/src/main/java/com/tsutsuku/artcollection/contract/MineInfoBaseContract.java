package com.tsutsuku.artcollection.contract;

import android.content.Intent;

import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/14
 * @Description
 */

public class MineInfoBaseContract {
public interface View{
    void setAvatar(String avatar);
    void setNick(String nick);
    void setSex(String sex);
}

public interface Presenter extends BasePresenter<View>{
    void setAvatar();
    void setNick(String oldNick);
    void setSex(String oldSex);

    void parseResult(int requestCode, Intent data);
}

}