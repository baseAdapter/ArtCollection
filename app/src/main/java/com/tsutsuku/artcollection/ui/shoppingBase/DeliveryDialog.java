package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/2/12
 * @Description
 */

public class DeliveryDialog {

    @BindView(R.id.rvDialog)
    RecyclerView rvDialog;
    @BindView(R.id.btnClose)
    Button btnClose;

    private Context context;
    private List<ItemVendor.InfoBean.DeliveryBean> list;
    private BaseRvAdapter adapter;
    private ItemVendor.InfoBean info;
    private DialogPlus deliveryDialog;
    private OnItemSimpleClickListener listener;

    public DeliveryDialog(Context context, OnItemSimpleClickListener listener) {
        this.context = context;
        this.listener = listener;
        list = new ArrayList<>();
        initDeliveryDialog();
    }

    private void initDeliveryDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_delivery, null);

        deliveryDialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(view))
                .setGravity(Gravity.BOTTOM)
                .create();

        ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDialog.setLayoutManager(layoutManager);

        adapter = new BaseRvAdapter<ItemVendor.InfoBean.DeliveryBean>(list) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new DeliveryAdapterItem(new OnItemSimpleClickListener<ItemVendor.InfoBean.DeliveryBean>() {
                    @Override
                    public void onItemClick(ItemVendor.InfoBean.DeliveryBean item) {
                        for (ItemVendor.InfoBean.DeliveryBean bean : info.getDelivery()) {
                            bean.setCheck(false);
                        }
                        item.setCheck(true);
                        info.setBean(item);

                        listener.onItemClick(info);
                        deliveryDialog.dismiss();
                    }
                });
            }
        };

        rvDialog.setAdapter(adapter);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryDialog.dismiss();
            }
        });
    }

    public void showDialog(ItemVendor.InfoBean item) {
        this.info = item;

        list.clear();
        list.addAll(item.getDelivery());
        adapter.notifyDataSetChanged();

        deliveryDialog.show();
    }
}
