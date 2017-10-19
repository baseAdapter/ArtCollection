package com.tsutsuku.artcollection.contract.login;

import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description Content
 */

public class ResetPasswordContract {
public interface View{
    void finishReset(String account, String password);

    void setCaptchaTime(int time);
}

public interface Presenter extends BasePresenter<View>{
    void getCaptcha(String account);

    void reset(String account, String password, String captcha);
}

public interface Model{
}


}