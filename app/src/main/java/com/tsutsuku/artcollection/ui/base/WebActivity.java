package com.tsutsuku.artcollection.ui.base;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tsutsuku.artcollection.R;


/**
 * Created by sunrenwei on 2016/6/16.
 */
public class WebActivity extends BaseActivity {

    private static final String LINK_URL = "linkUrl";
    private static final String TITLE = "title";

    public static Intent createIntent(Context context, String linkUrl) {
        return new Intent(context, WebActivity.class).putExtra(LINK_URL, linkUrl);
    }

    public static void launch(Context context, String linkUrl){
        launch(context, linkUrl, "");
    }

    public static void launch(Context context, String linkUrl, String title){
        context.startActivity(new Intent(context, WebActivity.class)
                .putExtra(LINK_URL, linkUrl)
                .putExtra(TITLE, title));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_web);
    }

    @Override
    public void initViews() {
        initTitle(getIntent().getStringExtra(TITLE));

        WebView webView = (WebView) findViewById(R.id.webView);
        final ProgressBar pbWeb = (ProgressBar) findViewById(R.id.pbWeb);

        webView.loadUrl(getIntent().getStringExtra(LINK_URL));

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

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    pbWeb.setProgress(newProgress);
                } else {
                    pbWeb.setProgress(100);
                    AlphaAnimation animation = new AlphaAnimation(1, 0);
                    animation.setDuration(500);
                    pbWeb.startAnimation(animation);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pbWeb.setVisibility(View.GONE);
                        }
                    }, 500);
                }

                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

}
