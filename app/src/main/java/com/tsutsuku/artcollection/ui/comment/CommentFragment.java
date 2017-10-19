package com.tsutsuku.artcollection.ui.comment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.CommentContract;
import com.tsutsuku.artcollection.im.Constant;
import com.tsutsuku.artcollection.model.comment.CommentFoldBean;
import com.tsutsuku.artcollection.presenter.comment.CommentFoldPresenterImpl;
import com.tsutsuku.artcollection.presenter.comment.CommentUnfoldPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragment;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.KeyBoardUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2017/1/13
 * @Description Content
 */

public class CommentFragment extends BaseFragment implements CommentContract.View, View.OnClickListener {
    public static final String MSG_ID = "msgId";
    private static final String HIDE = "hide";
    private static final String TYPE = "type";
    private static final String DTYPE = "dType"; // 二级Type
    private static final String DATA = "data";
    private static final int TYPE_UNFOLD = 0;
    private static final int TYPE_FOLD = 1;

    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private EditText etComment;
    private Button btnComment;
    private FrameLayout flSend;
    private TextView tvComment;
    private FrameLayout flComment;
    private FrameLayout view;

    private int type;

    public static CommentFragment newTypeUnfoldInstance(String msgId, boolean hide) {
        CommentFragment fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MSG_ID, msgId);
        bundle.putBoolean(HIDE, hide);
        bundle.putInt(TYPE, TYPE_UNFOLD);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static CommentFragment newTypeFoldInstance(String toId, boolean hide, String dType, List<CommentFoldBean> list) {
        CommentFragment fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MSG_ID, toId);
        bundle.putBoolean(HIDE, hide);
        bundle.putInt(TYPE, TYPE_FOLD);
        bundle.putString(DTYPE, dType);
        bundle.putParcelableArrayList(DATA, (ArrayList) list);
        fragment.setArguments(bundle);
        return fragment;
    }

    CommentUnfoldPresenterImpl unfoldPresenter;
    CommentFoldPresenterImpl foldPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
        type = getArguments().getInt(TYPE);
        if (type == TYPE_UNFOLD) {
            unfoldPresenter = new CommentUnfoldPresenterImpl(context, getArguments().getString(MSG_ID));
            unfoldPresenter.attachView(this);
        } else {
            foldPresenter = new CommentFoldPresenterImpl(context, getArguments().getString(MSG_ID), getArguments().getString(DTYPE));
            foldPresenter.attachView(this);
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBase.setLayoutManager(layoutManager);
        setCommentBar();
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        view.setVisibility(getArguments().getBoolean(HIDE) ? View.GONE : View.VISIBLE);
        rvBase.setAdapter(type == TYPE_UNFOLD ? unfoldPresenter.getAdapter() : foldPresenter.getAdapter());
        if (type == TYPE_FOLD) {
            ArrayList<CommentFoldBean> tempList = getArguments().getParcelableArrayList(DATA);
            foldPresenter.setData(tempList);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (type == TYPE_UNFOLD) {
            unfoldPresenter.detachView();
        } else {
            foldPresenter.detachView();
        }
    }

    @Override
    public void showInputBox(String toUserName) {
        flSend.setVisibility(View.VISIBLE);
        flComment.setVisibility(View.GONE);
        etComment.setHint(TextUtils.isEmpty(toUserName) ? "" : "回复" + toUserName + "：");
        requestCommentFocus();
        KeyBoardUtils.openKeyboard(context, etComment);

        if (TextUtils.isEmpty(toUserName)) {
            if (type == TYPE_UNFOLD) {
                unfoldPresenter.resetPId();
            } else {
                foldPresenter.resetPId();
            }
        }
    }

    @Override
    public void cleanComment() {
        etComment.setText("");
        KeyBoardUtils.closeKeyboard(getActivity());
    }

    private void setCommentBar() {
        ViewGroup parent = (ViewGroup) getActivity().findViewById(android.R.id.content);
        view = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_comment, null);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        etComment = (EditText) view.findViewById(R.id.etComment);
        btnComment = (Button) view.findViewById(R.id.btnComment);
        flSend = (FrameLayout) view.findViewById(R.id.flSend);
        tvComment = (TextView) view.findViewById(R.id.tvComment);
        flComment = (FrameLayout) view.findViewById(R.id.flComment);

        btnComment.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        flSend.setOnClickListener(this);

        parent.addView(view);
    }

    public void showInputBox() {
        view.setVisibility(View.VISIBLE);
    }

    public void hideInputBox() {
        view.setVisibility(View.GONE);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnComment: {
                if (TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))) {
                    LoginActivity.launch(context);
                } else {
                    if (type == TYPE_UNFOLD) {
                        unfoldPresenter.comment(etComment.getText().toString().trim());
                    } else {
                        foldPresenter.comment(etComment.getText().toString().trim());
                    }
                    hideSend();
                }
            }

            break;
            case R.id.tvComment:
                showInputBox("");
                break;
            case R.id.flSend:
                hideSend();
                break;
        }
    }

    private void hideSend() {
        flSend.setVisibility(View.GONE);
        flComment.setVisibility(View.VISIBLE);
    }

    private void requestCommentFocus() {
        etComment.setFocusable(true);
        etComment.setFocusableInTouchMode(true);
        etComment.requestFocus();
    }
}
