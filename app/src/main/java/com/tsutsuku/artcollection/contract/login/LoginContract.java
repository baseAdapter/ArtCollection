package com.tsutsuku.artcollection.contract.login;

import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/3
 * @Description Content
 */

public class LoginContract {
public interface View{
}

public interface Presenter extends BasePresenter<View>{
    void login(String account, String password);
}

public interface Model{
}


}