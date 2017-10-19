package com.tsutsuku.artcollection.other.custom.contract;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.other.custom.model.CustomDetailInfo;

/**
 * @Author Tsutsuku
 * @Create 2017/6/8
 * @Description
 */

public class CustomDetailContract {
    
public interface View{
    void setInfo(CustomDetailInfo info);
}

public interface Presenter{
    void start();

    void setId(String diyId);
    void chat();
    void buy();
}

public interface Model{
    void getData(String diyId, HttpResponseHandler handler);
}


}