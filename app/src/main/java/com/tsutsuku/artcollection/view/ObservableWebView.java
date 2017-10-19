package com.tsutsuku.artcollection.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @Author Tsutsuku
 * @Create 2017/3/7
 * @Description
 */

public class ObservableWebView extends WebView {
    private OnScrollChangedCallback mOnScrollChangedCallback;

    public ObservableWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ObservableWebView(Context context) {
        super(context);
    }

    public ObservableWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollChangedCallback != null){
            float webContent = getContentHeight() * getScale();// webview的高度
            float webNow = getHeight() + getScrollY();// 当前webview的高度
            if (Math.abs(webContent - webNow) < 1) {
                // 已经处于底端
                // Log.i("TAG1", "已经处于底端");
                mOnScrollChangedCallback.onPageEnd(l - oldl, t - oldt);
            } else if (getScrollY() == 0) {
                // Log.i("TAG1", "已经处于顶端");
                mOnScrollChangedCallback.onPageTop(l - oldl, t - oldt);
            } else {
                mOnScrollChangedCallback.onScrollChanged(l - oldl, t - oldt);
            }
        }
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public static interface OnScrollChangedCallback {
        public void onScrollChanged(int dx, int dy);
        public void onPageTop(int dx, int dy);
        public void onPageEnd(int dx, int dy);
    }
}
