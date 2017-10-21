package com.tsutsuku.artcollection.ui.shoppingBase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.shopping.ShoppingAddressContract;
import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.presenter.address.ShoppingAddressPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShoppingAddressActivity extends BaseActivity implements ShoppingAddressContract.View {
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.btnAdd)
    Button btnAdd;

    private static final String TYPE = "type";
    ShoppingAddressPresenterImpl presenter;
    int type;

    public static void launchTypeEdit(Context context) {
        context.startActivity(new Intent(context, ShoppingAddressActivity.class)
                .putExtra(TYPE, ShoppingAddressPresenterImpl.TYPE_EDIT));
    }

    public static void launchTypeView(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, ShoppingAddressActivity.class)
                .putExtra(TYPE, ShoppingAddressPresenterImpl.TYPE_VIEW), requestCode);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shopping_address);
    }

    @Override
    public void initViews() {

        ButterKnife.bind(this);

        presenter = new ShoppingAddressPresenterImpl(context, getIntent().getIntExtra(TYPE, ShoppingAddressPresenterImpl.TYPE_EDIT));
        presenter.attachView(this);
        type = getIntent().getIntExtra(TYPE, 0);
        initTitle(type == ShoppingAddressPresenterImpl.TYPE_EDIT ? R.string.mine_address : R.string.select_address);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBase.setLayoutManager(layoutManager);

        rvBase.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .size(DensityUtils.dp2px(10))
                .build());


    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        rvBase.setAdapter(presenter.getAdapter());
        presenter.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick(R.id.btnAdd)
    public void onClick() {
        ShoppingAddressDetailActivity.launch(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            presenter.parseResult(requestCode, data.getExtras());
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finishView(ItemAddress item) {
        setResult(RESULT_OK, new Intent().putExtra(Intents.ADDRESS, item));
        finish();
    }
}
