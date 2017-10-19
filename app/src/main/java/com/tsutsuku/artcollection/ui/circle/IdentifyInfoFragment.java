package com.tsutsuku.artcollection.ui.circle;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.IdentifyInfoContract;
import com.tsutsuku.artcollection.model.IdentifyInfoBean;
import com.tsutsuku.artcollection.presenter.IdentifyInfoPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragment;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.KeyBoardUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/13
 * @Description Content
 */

public class IdentifyInfoFragment extends BaseFragment implements IdentifyInfoContract.View {

    @BindView(R.id.tvNon)
    TextView tvNon;
    @BindView(R.id.tvResult)
    TextView tvResult;
    @BindView(R.id.tvYear)
    TextView tvYear;
    @BindView(R.id.tvValue)
    TextView tvValue;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvAppraiser)
    TextView tvAppraiser;
    @BindView(R.id.ivChat)
    ImageView ivChat;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.llInfo)
    RelativeLayout llInfo;

    private static final String MSG_ID = "msgId";
    private static final String PIC = "pic";
    private static final String NAME = "name";

    private boolean isBtnValid;
    private View identifyView;

    public IdentifyInfoPresenterImpl presenter;


    public static IdentifyInfoFragment newInstance(String msgId, String pic, String name) {
        IdentifyInfoFragment fragment = new IdentifyInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MSG_ID, msgId);
        bundle.putString(PIC, pic);
        bundle.putString(NAME, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_identify_info;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
        presenter = new IdentifyInfoPresenterImpl(getArguments().getString(MSG_ID));
        presenter.attachView(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public void showIdentifyButton() {
        if (isBtnValid) {
            identifyView.setVisibility(View.VISIBLE);
        }
    }

    public void hideIdentifyButton() {
        if (isBtnValid) {
            identifyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setData(IdentifyInfoBean bean) {
        if ("0".equals(bean.getCheckState())) {
            tvNon.setVisibility(View.VISIBLE);
            if (SharedPref.getString(Constants.USER_ID).equals(bean.getExpertId())) {
                isBtnValid = true;

                ViewGroup parent = (ViewGroup) getActivity().findViewById(android.R.id.content);
                identifyView = IdentifyDialog.getInstance().getDialog(context, getArguments().getString(PIC), getArguments().getString(NAME), new IdentifyDialog.Callback() {
                    @Override
                    public void identify(String year, String money, String meno) {
                        presenter.identify(year, money, meno);
                    }
                });
                parent.addView(identifyView);
            }
        } else {
            llInfo.setVisibility(View.VISIBLE);
            isBtnValid = false;
            SpannableStringBuilder builder = new SpannableStringBuilder("鉴定结果（仅供参考）");
            builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.d)), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(16)), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvResult.setText(builder);

            tvYear.setText("年代新旧：" + bean.getCheckYear());
            tvValue.setText("收藏价值：" + bean.getCheckMoney());
            tvContent.setText("意见参考：" + bean.getCheckMeno());
            tvTime.setText(bean.getCheckTime());
            tvAppraiser.setText("鉴定师：" + bean.getUserName());
        }
    }

    @Override
    public void hideDialog(IdentifyInfoBean bean) {
        identifyView.setVisibility(View.GONE);
        KeyBoardUtils.closeKeyboard(getActivity());
        setData(bean);
    }

    public static class IdentifyDialog {
        @BindView(R.id.vBg)
        View vBg;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.etYear)
        EditText etYear;
        @BindView(R.id.etValue)
        EditText etValue;
        @BindView(R.id.etContent)
        EditText etContent;
        @BindView(R.id.ivClose)
        ImageView ivClose;
        @BindView(R.id.ivPic)
        ImageView ivPic;
        @BindView(R.id.btnIdentify)
        Button btnIdentify;
        @BindView(R.id.flIdentify)
        FrameLayout flIdentify;

        private static volatile IdentifyDialog instance;
        private Callback callback;
        private IdentifyInfoBean bean;

        public static IdentifyDialog getInstance() {
            if (instance == null) {
                synchronized (IdentifyDialog.class) {
                    if (instance == null) {
                        instance = new IdentifyDialog();
                    }
                }
            }
            return instance;
        }

        public View getDialog(Context context, String pic, String name, Callback callback) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_identify, null);
            this.callback = callback;
            ButterKnife.bind(this, view);

            Glide.with(context).load(pic).into(ivPic);
            tvName.setText(name);
            CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);
            return view;
        }

        @OnClick({R.id.ivClose, R.id.btnIdentify, R.id.vBg, R.id.flIdentify})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.vBg:
                case R.id.ivClose:
                    flIdentify.setVisibility(View.GONE);
                    break;
                case R.id.btnIdentify:
                    if (flIdentify.getVisibility() == View.GONE) {
                        flIdentify.setVisibility(View.VISIBLE);
                    } else {
                        callback.identify(etYear.getText().toString().trim(),
                                etValue.getText().toString().trim(),
                                etContent.getText().toString().trim());
                    }
                    break;
                case R.id.flIdentify:
                    break;
            }
        }

        interface Callback {
            void identify(String year, String money, String meno);
        }
    }
}
