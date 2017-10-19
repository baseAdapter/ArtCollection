package com.tsutsuku.artcollection.presenter.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.CommentContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemClickListener;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ItemNews;
import com.tsutsuku.artcollection.model.comment.CommentFoldBean;
import com.tsutsuku.artcollection.model.comment.CommentUnfoldBean;
import com.tsutsuku.artcollection.model.comment.ReplayCommnetsBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.comment.CommentFoldAdapterItem;
import com.tsutsuku.artcollection.ui.comment.CommentReplayAdapterItem;
import com.tsutsuku.artcollection.ui.comment.CommentUnfoldAdapterItem;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.TimeUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/01/17
 */

public class CommentFoldPresenterImpl implements CommentContract.Presenter {
    private CommentContract.View view;
    private String toId;
    private BaseRvAdapter adapter;
    private List<Object> viewList;
    private List<CommentFoldBean> dataList;
    private String ctype;

    private String mPcommentId;
    private String mToUserId;
    private String mToUserName;
    private Context context;

    public CommentFoldPresenterImpl(Context context, String toId, String ctype) {
        this.context = context;
        this.toId = toId;
        this.ctype = ctype;
        viewList = new ArrayList<>();
    }

    @Override
    public void refresh() {
    }

    @Override
    public void comment(final String content) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Video.addComment");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("toId", toId);
        hashMap.put("ctype", ctype);
        hashMap.put("pId", mPcommentId);
        hashMap.put("toUserId", mToUserId);
        hashMap.put("content", content);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    view.cleanComment();

                    if (mPcommentId.equals("0")) {
                        CommentFoldBean bean = new CommentFoldBean();
                        bean.setCommentId(data.getString("commentId"));
                        bean.setComTime(TimeUtils.getDate());
                        bean.setUserId(SharedPref.getString(Constants.USER_ID));
                        bean.setNickName(SharedPref.getString(Constants.NICK));
                        bean.setPhoto(SharedPref.getString(Constants.AVATAR));
                        bean.setComcontent(content);
                        bean.setReplay_commnets(new ArrayList<ReplayCommnetsBean>());
                        dataList.add(0, bean);
                        viewList.add(0, bean);
                        adapter.notifyDataSetChanged();
                    } else {
                        ReplayCommnetsBean item = new ReplayCommnetsBean();
                        item.setCommentId(data.getString("commentId"));
                        item.setUserId(SharedPref.getString(Constants.USER_ID));
                        item.setDisplayName(SharedPref.getString(Constants.NICK));
                        item.setComTime(TimeUtils.getDate());
                        item.setComcontent(content);
                        item.setToDisplayName(mToUserName);

                        for (CommentFoldBean bean : dataList) {
                            if (bean.getCommentId().equals(mPcommentId)) {
                                int addPosition = viewList.indexOf(bean);
                                bean.getReplay_commnets().add(item);
                                viewList.add(addPosition + bean.getReplay_commnets().size(), item);
                                adapter.notifyDataSetChanged();
                                return;
                            }
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
    public void resetPId() {
        mToUserId = "0";
        mPcommentId = "0";
    }

    @Override
    public BaseAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<Object>(viewList) {

                @Override
                public Object getItemType(Object o) {
                    return o instanceof CommentFoldBean ? "comment" : "replay";
                }

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object item) {

                    switch ((String) item) {
                        case "comment": {
                            return new CommentFoldAdapterItem(new OnItemSimpleClickListener<CommentFoldBean>() {
                                @Override
                                public void onItemClick(CommentFoldBean item) {
                                    if (item.getUserId().equals(SharedPref.getString(Constants.USER_ID))) {
                                        deleteComment(item.getCommentId());
                                    } else {
                                        view.showInputBox(item.getNickName());
                                        mPcommentId = item.getCommentId();
                                        mToUserId = item.getUserId();
                                        mToUserName = item.getNickName();
                                    }
                                }
                            });
                        }
                        default: {
                            return new CommentReplayAdapterItem(new OnItemSimpleClickListener<ReplayCommnetsBean>() {
                                @Override
                                public void onItemClick(ReplayCommnetsBean item) {
                                    if (item.getUserId().equals(SharedPref.getString(Constants.USER_ID))) {
                                        deleteComment(item.getCommentId());
                                    } else {
                                        view.showInputBox(item.getDisplayName());
                                        for (CommentFoldBean bean : dataList) {
                                            if (bean.getReplay_commnets().contains(item)) {
                                                mPcommentId = bean.getCommentId();
                                                mToUserId = item.getUserId();
                                                mToUserName = item.getDisplayName();
                                                return;
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            };
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
        hashMap.put("service", "Video.delComment");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("commentId", commentId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    for (CommentFoldBean bean : dataList) {
                        if (bean.getCommentId().equals(commentId)) {
                            dataList.remove(bean);
                            viewList.remove(bean);
                            for (ReplayCommnetsBean replay : bean.getReplay_commnets()) {
                                viewList.remove(replay);
                            }
                            adapter.notifyDataSetChanged();

                            return;
                        }

                        for (ReplayCommnetsBean replay : bean.getReplay_commnets()) {
                            if (replay.getCommentId().equals(commentId)) {
                                bean.getReplay_commnets().remove(replay);
                                int position = viewList.indexOf(replay);
                                viewList.remove(position);
                                adapter.notifyItemRemoved(position);
                                return;
                            }
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

    public void setData(List<CommentFoldBean> list) {
        dataList = list;
        for (CommentFoldBean bean : dataList) {
            viewList.add(bean);
            for (ReplayCommnetsBean replay : bean.getReplay_commnets()) {
                viewList.add(replay);
            }
        }
        adapter.notifyDataSetChanged();
    }

}