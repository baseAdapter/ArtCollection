package com.tsutsuku.artcollection.ui.news;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.ShareUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by sunrenwei on 2016/6/16.
 */
public class NewsDetailActivity extends BaseActivity {

    private static final String LINK_URL = "linkUrl";
    private static final String TITLE = "title";
    private static final String PIC = "pic";

    public static Intent createIntent(Context context, String linkUrl) {
        return new Intent(context, NewsDetailActivity.class).putExtra(LINK_URL, linkUrl);
    }

    public static void launch(Context context, String linkUrl, String title, String pic) {
        context.startActivity(new Intent(context, NewsDetailActivity.class)
                .putExtra(LINK_URL, linkUrl)
                .putExtra(TITLE, title)
                .putExtra(PIC, pic));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_news_detail);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);

        initTitle(getIntent().getStringExtra(TITLE), R.drawable.icon_share_xxhdpi);

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

    @OnClick(R.id.ivTitleButton)
    public void onViewClicked() {
        ShareUtils.showShare(context, getIntent().getStringExtra(PIC),
                getIntent().getStringExtra(TITLE),
                SharedPref.getSysString(Constants.Share.NEWS));
    }
}
