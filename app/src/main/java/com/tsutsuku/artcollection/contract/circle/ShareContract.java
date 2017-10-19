package com.tsutsuku.artcollection.contract.circle;

import android.content.Context;

import com.tsutsuku.artcollection.model.ItemExport;

/**
 * @Author Sun Renwei
 * @Create 2017/1/10
 * @Description
 */

public class ShareContract extends ShareBaseContract {
    public interface View extends ShareBaseContract.View{
        void setTypeIdentify();
        void setType(String type);
        void setPublishType(boolean isFree);
        void setExpert(ItemExport item);
        Context getContext();
    }

    public interface Presenter extends ShareBaseContract.Presenter{
        void share(String title, String content, String useMoney, boolean agree);
        void setPublishType(int publishType);
        void getType();
        void getExpert(boolean isFree);
    }
}
