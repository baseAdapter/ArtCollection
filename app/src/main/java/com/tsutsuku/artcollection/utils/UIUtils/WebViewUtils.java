package com.tsutsuku.artcollection.utils.UIUtils;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @Author Tsutsuku
 * @Create 2017/6/20
 * @Description
 */

public class WebViewUtils {
    /**
     * webView 装载 url
     * @param view webView
     * @param url Url
     */
    public static void loadUrl(WebView view, String url) {
        view.loadUrl(url);

        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);

        view.setWebViewClient(new WebViewClient() {
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
