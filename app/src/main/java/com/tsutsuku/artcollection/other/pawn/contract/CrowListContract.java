package com.tsutsuku.artcollection.other.pawn.contract;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.other.pawn.model.ItemCrowdType;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/6/26
 * @Description
 */

public class CrowListContract {
    
public interface View{
    void setData(int total, List<ItemCrowdType> list);
    void addData(List<ItemCrowdType> list);
}

public interface Presenter{
    void share();
    void getData(String pageIndex);

}

public interface Model{
    void getData(String id, String pageIndex, HttpResponseHandler handler);
}


}