package com.tsutsuku.artcollection.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @Author Sun Renwei
 * @Create 2017/2/13
 * @Description Content
 */

public class NestedWebFragment extends Fragment {
    private static final String URL = "url";

    private Context context;
    private NestedScrollView scrollView;
    private WebView webView;

    public static NestedWebFragment newInstance(String url) {
        NestedWebFragment fragment = new NestedWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        scrollView = new NestedScrollView(context);
        webView = new WebView(context);
        scrollView.addView(webView);
        initViews();
        return scrollView;
    }

    private void initViews() {
        webView.loadUrl(getArguments().getString(URL));

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    view.loadUrl(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
}
