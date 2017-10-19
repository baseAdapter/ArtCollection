package com.tsutsuku.artcollection.ui.wallet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.wallet.ItemPayType;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2017/2/15
 * @Description 支付弹窗
 */

public class PayTypeDialog {
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    private DialogPlus dialog;
    private Context context;
    private List<ItemPayType> list;

    private CallBack callBack;

    public PayTypeDialog(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        initDialog();
    }

    private void initDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_pay_type, null);
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(400)));
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBase.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        list.add(new ItemPayType("支付宝", R.drawable.icon_alipay, "alipay"));
        list.add(new ItemPayType("微信支付", R.drawable.icon_wxpay, "wxpay"));
        rvBase.setAdapter(new BaseRvAdapter<ItemPayType>(list) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new PayTypeAdapterItem(new OnItemSimpleClickListener<ItemPayType>() {
                    @Override
                    public void onItemClick(ItemPayType item) {
                        dialog.dismiss();
                        callBack.finish(item.getCode());
                    }
                });
            }
        });

        dialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(view))
                .setGravity(Gravity.BOTTOM)
                .create();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public interface CallBack {
        void finish(String payType);
    }

    public void show() {
        show(true);
    }

    public void show(boolean withBalance) {
        if (withBalance && !"balance".equals(list.get(0).getCode())) {
            list.add(0, new ItemPayType("余额", R.mipmap.ic_launcher, "balance"));
        }
        dialog.show();
    }
}
