package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.shopping.OrderBean;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/16
 * @Description 订单评价
 */

public class OrderCommentActivity extends BaseActivity {
    private static final String ORDER_ID = "orderId";
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.srbDesc)
    SimpleRatingBar srbDesc;
    @BindView(R.id.srbService)
    SimpleRatingBar srbService;
    @BindView(R.id.btnCmd)
    Button btnCmd;

    private String orderId;

    public static void launch(Context context, String orderId) {
        context.startActivity(new Intent(context, OrderCommentActivity.class).putExtra(ORDER_ID, orderId));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_order_comment);
    }

    @Override
    public void initViews() {
        initTitle("订单评价");
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        orderId = getIntent().getStringExtra(ORDER_ID);
    }

    @OnClick(R.id.btnCmd)
    public void onClick() {
        comment(srbDesc.getRating(), srbService.getRating(), etComment.getText().toString().trim());
    }

    private void comment(float pDesc, float pService, String contents) {
        if (TextUtils.isEmpty(contents)) {
            ToastUtils.showMessage(R.string.input_comment);
        } else if (pDesc == 0) {
            ToastUtils.showMessage(R.string.comment_vendor_description);
        } else if (pService == 0) {
            ToastUtils.showMessage(R.string.comment_vendor_service);
        } else {
            commentImpl(pDesc, pService, contents);
        }
    }

    private void commentImpl(float pDesc, float pService, String contents) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Order.commentOrder");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("orderId", orderId);
        hashMap.put("point", String.valueOf(pDesc));
        hashMap.put("point2", String.valueOf(pService));
        hashMap.put("contents", contents);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    OrderBean bean = new OrderBean();
                    bean.setOrderId(orderId);
                    RxBus.getDefault().post(BusEvent.SHOPPING, bean);
                    finish();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}
