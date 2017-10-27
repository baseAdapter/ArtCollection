package com.tsutsuku.artcollection.contract.login;

import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description Content
 */

public class RegisterContract {
    public interface View {
        void setCaptchaTime(int time);
        void registerSuccess();
    }

    public interface Presenter extends BasePresenter<View> {
        void register(String nickname,String account, String password, String captcha, boolean agree);

        void getCaptcha(String account);
    }

    public interface Model {
    }


}