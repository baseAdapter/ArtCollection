package com.tsutsuku.artcollection.ui.shoppingBase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.shopping.ShoppingAddressDetailContract;
import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.presenter.address.ShoppingAddressDetailPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class ShoppingAddressDetailActivity extends BaseActivity implements ShoppingAddressDetailContract.View {
    public static final String ADD_ADDRESS = "addAddress";
    public static final String EDIT_ADDRESS = "editAddress";

    public static final String TYPE = "type";
    public static final String ITEM = "item";

    private static final int REQUEST_CODE = 0;

    ShoppingAddressDetailPresenterImpl presenter;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etDetail)
    EditText etDetail;
    @BindView(R.id.cbDefault)
    CheckBox cbDefault;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.rlRegion)
    RelativeLayout rlRegion;
    @BindView(R.id.tvRegion)
    TextView tvRegion;
    @BindView(R.id.llDefault)
    LinearLayout llDefault;

    private AreaDialog areaDialog;

    public static void launch(Context context, ItemAddress item) {
        ((Activity)context).startActivityForResult(new Intent(context, ShoppingAddressDetailActivity.class)
                .putExtra(TYPE, EDIT_ADDRESS)
                .putExtra(ITEM, item), REQUEST_CODE);
    }

    public static void launch(Context context) {
        ((Activity)context).startActivityForResult(new Intent(context, ShoppingAddressDetailActivity.class)
                .putExtra(TYPE, ADD_ADDRESS), REQUEST_CODE);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shopping_address_edit);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        presenter = new ShoppingAddressDetailPresenterImpl();
        presenter.attachView(this);
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {
        areaDialog = new AreaDialog(context, presenter, new AreaDialog.viewInterface() {
            @Override
            public void setRegion(String region) {
                tvRegion.setText(region);
            }
        });

        if (ADD_ADDRESS.equals(getIntent().getStringExtra(TYPE))) {
            initTitle(R.string.add_address);
        } else {
            initTitle(R.string.edit_address);
            ItemAddress item = getIntent().getParcelableExtra(ITEM);
            areaDialog.setData(item.getProvince(), item.getCity(), item.getCounty());
            etName.setText(item.getConsigneeName());
            etMobile.setText(item.getContactNumber());
            etDetail.setText(item.getDetailAddress());

            presenter.setProvince(item.getProvince());
            presenter.setCity(item.getCity());
            presenter.setArea(item.getCounty());
            presenter.setAddressId(item.getAddressId());

            tvRegion.setText(item.getProvince() + item.getCity() + item.getCounty());
            cbDefault.setChecked("1".equals(item.getIsDefault()));

            if ("1".equals(item.getIsDefault())){
                llDefault.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.btnSave, R.id.rlRegion})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave: {
                presenter.addAddress(etName.getText().toString().trim(),
                        etMobile.getText().toString().trim(),
                        etDetail.getText().toString().trim(),
                        cbDefault.isChecked());
            }
            break;
            case R.id.rlRegion: {
                areaDialog.show();
            }
            break;
        }
    }

    @Override
    public void finishView(ItemAddress item) {
        setResult(RESULT_OK, new Intent().putExtra(Intents.ADDRESS, item));
        finish();
    }
}
