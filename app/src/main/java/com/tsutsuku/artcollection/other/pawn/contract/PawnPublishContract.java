package com.tsutsuku.artcollection.other.pawn.contract;

import com.tsutsuku.artcollection.contract.circle.ShareBaseContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/6/25
 * @Description
 */

public class PawnPublishContract {
    
public interface View extends ShareBaseContract.View{
    void finish();
}

public interface Presenter{
    void share(String title, String type, String size, String content, String tel, String city, List<String> list);
}

public interface Model{
    void share(String title, String type, String size, String content, String tel, String city, List<String> list, HttpResponseHandler handler);
}


}