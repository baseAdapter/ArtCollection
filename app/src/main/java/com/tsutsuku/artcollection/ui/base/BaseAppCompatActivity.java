package com.tsutsuku.artcollection.ui.base;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.utils.TLog;


/**
 * Created by tsutsuku on 2016/5/28.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().toString();
    protected LinearLayout llBack;
    protected TextView tvTitle;
    protected TextView tvTitleButton;
    protected ImageView ivTitleButton;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView();
        initViews();
        initListeners();
        initData();

        TLog.i("current activity is" + TAG);
    }
    protected abstract void setContentView();

    protected abstract void initViews();

    protected abstract void initListeners();

    protected abstract void initData();

    protected void initTitle(String title) {
        llBack = (LinearLayout) findViewById(R.id.llBack);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                closeKeyboard();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);
    }

    protected void initTitle(int title) {
        initTitle(getString(title));
    }

    protected void initTitle(String title, String btn) {
        this.initTitle(title);
        tvTitleButton = (TextView) findViewById(R.id.tvTitleButton);
        tvTitleButton.setText(btn);
    }

    protected void initTitle(String title, int drawableId) {
        this.initTitle(title);
        ivTitleButton = (ImageView) findViewById(R.id.ivTitleButton);
        ivTitleButton.setImageResource(drawableId);
    }

    protected void initTitle(int title, int ResId) {
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
     * Close soft keyboard
     */
    protected void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * detect whether soft keyboard show
     *
     * @param rootView 根View
     * @return
     */
    protected boolean isKeyboardShow(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }
}
