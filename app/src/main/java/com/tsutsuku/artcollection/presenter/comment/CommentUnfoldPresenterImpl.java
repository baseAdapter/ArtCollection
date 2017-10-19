package com.tsutsuku.artcollection.presenter.comment;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.CommentContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.comment.CommentUnfoldBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.comment.CommentUnfoldAdapterItem;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/01/17
 */

public class CommentUnfoldPresenterImpl implements CommentContract.Presenter {
    private CommentContract.View view;
    private String msgId;
    private BaseRvAdapter adapter;
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<CommentUnfoldBean>>() {
    }.getType();

    private Context context;
    private String mPcommentId;
    private String mToUserId;
    private ArrayList<CommentUnfoldBean> list;

    public CommentUnfoldPresenterImpl(Context context, String msgId) {
        this.context = context;
        this.msgId = msgId;
        list = new ArrayList<>();
    }

    @Override
    public void refresh() {
        getData();
    }

    @Override
    public void comment(String content) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.commentMessage");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("pcommentId", mPcommentId);
        hashMap.put("msgId", msgId);
        hashMap.put("toUserId", mToUserId);
        hashMap.put("content", content);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    getData();
                    view.cleanComment();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public void resetPId() {
        mToUserId = "0";
        mPcommentId = "0";
    }

    @Override
    public BaseAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<CommentUnfoldBean>(list) {

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object item) {
                    return new CommentUnfoldAdapterItem(new OnItemSimpleClickListener<CommentUnfoldBean>() {
                        @Override
                        public void onItemClick(CommentUnfoldBean item) {
                            if (item.getUserId().equals(SharedPref.getString(Constants.USER_ID))) {
                                deleteComment(item.getCommentId());
                            } else {
                                view.showInputBox(item.getToUserName());
                                mPcommentId = item.getCommentId();
                                mToUserId = item.getUserId();
                            }
                        }
                    });
                }
            };
            getData();
        }
        return adapter;
    }

    private void deleteComment(final String commentId) {
        new MaterialDialog.Builder(context)
                .title("提示")
                .content("确认删除？")
                .positiveText(context.getString(R.string.ok))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteCommentImpl(commentId);
                    }
                })
                .negativeText(context.getString(R.string.cancel))
                .show();
    }

    private void deleteCommentImpl(final String commentId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.deleteComment");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("commentId", commentId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    for(CommentUnfoldBean bean : list){
                        if (commentId.equals(bean.getCommentId())){
                            list.remove(bean);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
    @Override
    public void attachView(CommentContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.getMessageComments");
        hashMap.put("msgId", msgId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    list.clear();
                    list.addAll(((List<CommentUnfoldBean>) gson.fromJson(data.getString("list"), type)));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

}