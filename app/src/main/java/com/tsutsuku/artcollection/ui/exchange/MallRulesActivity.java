package com.tsutsuku.artcollection.ui.exchange;



import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.XmlPaser;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MallRulesActivity extends BaseActivity {
    @BindView(R.id.mallRulesTv)
    TextView mallRulesTv;

    private XmlPaser xmlPaser = new XmlPaser();
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mall_rules);
    }

    @Override
    public void initViews() {
        initTitle(R.string.exchange_rules);
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        try {
            InputStream inputStream = this.getResources().getAssets().open("MallRules.xml");
            xmlPaser.doPaserd(mallRulesTv, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
