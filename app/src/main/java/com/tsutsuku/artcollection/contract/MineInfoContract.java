package com.tsutsuku.artcollection.contract;

/**
 * @Author Sun Renwei
 * @Create 2017/1/14
 * @Description
 */

public class MineInfoContract {
public interface View extends MineInfoBaseContract.View{
    void setMobile(String mobile);
}

public interface Presenter extends MineInfoBaseContract.Presenter{
    void setMobile(String oldMobile);
}



}