package com.tsutsuku.artcollection.presenter.lesson;

import android.content.Context;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.LessonDetailContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.LessonBean;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.ShareUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/03/06
 */

public class LessonDetailPresenterImpl implements LessonDetailContract.Presenter {

    private LessonDetailContract.View view;
    private LessonBean bean;
    private String lessonId;
    private Context context;

    public LessonDetailPresenterImpl(Context context, String lessonId) {
        this.context = context;
        this.lessonId = lessonId;
    }

    @Override
    public void attachView(LessonDetailContract.View view) {
        this.view = view;
        getData();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Video.getVideoInfo");
        hashMap.put("videoId", lessonId);
        hashMap.put("user_Id", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    bean = GsonUtils.parseJson(data.getString("info"), LessonBean.class);
                    view.setViews(bean);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    public void collection() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", bean.getIsCollection() == 1 ? "Collections.delete" : "Collections.add");
        hashMap.put("pId", lessonId);
        hashMap.put("ctype", "1");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    bean.setIsCollection(bean.getIsCollection() == 0 ? 1 : 0);
                    view.setCollect(bean.getIsCollection() == 1);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public void share() {
        ShareUtils.showShare(context, bean.getVideoLogo(), bean.getVideoTitle(), SharedPref.getSysString(Constants.Share.VIDEO) + lessonId);
    }
}