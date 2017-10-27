package com.tsutsuku.artcollection.ui.exchange;



import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.XmlPaser;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntegrationRulesActivity extends BaseActivity {
    @BindView(R.id.integrationRulesTv)
    TextView mIntegrationRulesTv;

    private XmlPaser xmlPaser = new XmlPaser();
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_integration_rules);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        try {
            InputStream inputStream = this.getResources().getAssets().open("Integration.xml");
            xmlPaser.doPaserd(mIntegrationRulesTv, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
