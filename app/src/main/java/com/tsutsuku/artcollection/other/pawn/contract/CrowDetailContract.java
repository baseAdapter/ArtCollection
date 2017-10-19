package com.tsutsuku.artcollection.other.pawn.contract;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.other.pawn.model.CrowdInfo;

/**
 * @Author Tsutsuku
 * @Create 2017/6/26
 * @Description
 */

public class CrowDetailContract {

public interface View{
    void setData(CrowdInfo info);
}

public interface Presenter{
    void share();
    void start();
    void support();
}

public interface Model{
    void getData(String id, HttpResponseHandler handler);
}


}