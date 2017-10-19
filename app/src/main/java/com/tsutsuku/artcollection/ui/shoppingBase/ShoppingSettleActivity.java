package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.shopping.ShoppingSettleContract;
import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.presenter.shopping.ShoppingSettlePresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.wallet.PayTypeDialog;
import com.tsutsuku.artcollection.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/2/9
 * @Description 商品结算页面
 */

public class ShoppingSettleActivity extends BaseActivity implements ShoppingSettleContract.View {
    private static final String PRODUCTS_INFO = "productsInfo";
    private static final String TYPE = "type";

    private static final int EDIT_VIEW = 1;

    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.btnCmd)
    Button btnCmd;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.flAddress)
    FrameLayout flAddress;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.llBack)
    LinearLayout llBack;

    ShoppingSettlePresenterImpl presenter;
    private PayTypeDialog payTypeDialog;

    public static void launch(Context context, String type, ArrayList<ItemVendor> settleList) {
        context.startActivity(new Intent(context, ShoppingSettleActivity.class)
                .putExtra(TYPE, type)
                .putParcelableArrayListExtra(PRODUCTS_INFO, settleList));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shopping_settle);
    }

    @Override
    public void initViews() {
        initTitle(R.string.confirm_orders);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBase.setLayoutManager(layoutManager);

        payTypeDialog = new PayTypeDialog(context, new PayTypeDialog.CallBack() {
            @Override
            public void finish(String payType) {
                presenter.submit(payType, getIntent().getStringExtra(TYPE));
            }
        });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        ArrayList<ItemVendor> dataList = getIntent().getParcelableArrayListExtra(PRODUCTS_INFO);
        presenter = new ShoppingSettlePresenterImpl(context, dataList);
        presenter.attachView(this);

        rvBase.setAdapter(presenter.getAdapter());
    }

    @Override
    public void setPrice(String price) {
        tvPrice.setText(price);
    }

    @Override
    public void setAddress(ItemAddress address) {
        flAddress.setVisibility(View.VISIBLE);
        btnEdit.setVisibility(View.GONE);

        tvName.setText(address.getConsigneeName());
        tvMobile.setText(address.getContactNumber());
        tvAddress.setText(address.getAddress());
    }

    @Override
    public void showAddressEdit(boolean show) {
        btnEdit.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void dealFinish() {
        finish();
    }

    @OnClick({R.id.flAddress, R.id.btnEdit, R.id.btnCmd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.flAddress:
            case R.id.btnEdit:
                ShoppingAddressActivity.launchTypeView(this, EDIT_VIEW);
                break;
            case R.id.btnCmd:
                if (TextUtils.isEmpty(tvAddress.getText().toString())) {
                    ToastUtils.showMessage("请选择收货地址");
                    return;
                }

                if ("4".equals(getIntent().getStringExtra(TYPE))) {
                    presenter.submit(null, "4");
                } else {
                    payTypeDialog.show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EDIT_VIEW:
                    ItemAddress item = data.getParcelableExtra(Intents.ADDRESS);
                    if (presenter.getAddressId() == null || !item.getAddressId().equals(presenter.getAddressId())) {
                        setAddress(item);
                        presenter.setAddress(item);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


}
