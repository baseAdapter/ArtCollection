package com.tsutsuku.artcollection.ui.base;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;


/**
 * Created by sunrenwei on 2016/6/4.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;
    protected View rootView;

    protected LinearLayout llBack;
    protected TextView tvTitle;
    protected TextView tvTitleEdit;
    protected TextView tvTitleButton;
    protected ImageView ivTitleButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        if (getLayoutId() != -1) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        initViews();
        initListeners();
        initData();
        return rootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void initListeners();

    protected abstract void initData();

    protected View findViewById(int layoutId) {
        return rootView.findViewById(layoutId);
    }

    protected void initTitle(String title) {
        llBack = (LinearLayout) findViewById(R.id.llBack);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                closeKeyboard();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);
    }

    protected void initTitle(String title, int ResId) {
        this.initTitle(title);
        if ("string".equals(getResources().getResourceTypeName(ResId))) {
            tvTitleButton = (TextView) findViewById(R.id.tvTitleButton);
            tvTitleButton.setText(ResId);
        } else if ("drawable".equals(getResources().getResourceTypeName(ResId))) {
            ivTitleButton = (ImageView) findViewById(R.id.ivTitleButton);
            ivTitleButton.setImageResource(ResId);
        } else if ("color".equals(getResources().getResourceTypeName(ResId))) {
            ivTitleButton = (ImageView) findViewById(R.id.ivTitleButton);
            ivTitleButton.setImageResource(ResId);
        }
    }

    /**
     * 收起键盘
     */
    protected void closeKeyboard() {
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 判断软键盘是否显示
     *
     * @param rootView 根View
     * @return
     */
    protected boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }
}
