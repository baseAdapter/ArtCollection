package com.tsutsuku.artcollection.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.CateBean;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2017/1/17
 * @Description 商品类别选择
 */

public class CateSelectActivity extends BaseActivity {
    private static final int TYPE_MENU = 0;
    private static final int TYPE_DETAIL = 1;
    private static final int ITEM_TITLE = 0;
    private static final int ITEM_CHILD = 1;
    private static final String TYPE = "cateType";

    @BindView(R.id.llBack)
    LinearLayout llBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rvMenu)
    RecyclerView rvMenu;
    @BindView(R.id.rvChild)
    RecyclerView rvChild;
    private List<CateBean> list;
    private List<Object> childList;

    private int cateType;
    private BaseRvAdapter childAdapter;

    public static void launchTypeMenu(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, CateSelectActivity.class)
                .putExtra(TYPE, TYPE_MENU), requestCode);
    }

    public static void launchTypeDetail(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, CateSelectActivity.class)
                .putExtra(TYPE, TYPE_DETAIL), requestCode);
    }

    public static void launchTypeDetail(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(new Intent(fragment.getContext(), CateSelectActivity.class)
                .putExtra(TYPE, TYPE_DETAIL), requestCode);
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_cate_select);
    }

    @Override
    public void initViews() {
        initTitle(R.string.cate);
        ButterKnife.bind(this);

        cateType = getIntent().getIntExtra(TYPE, TYPE_MENU);

        rvMenu.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        list = GsonUtils.parseJsonArray(SharedPref.getSysString(Constants.CATE_MORE), CateBean.class);
        if (cateType == TYPE_DETAIL) {
            list.get(0).setCheck(true);
        }
        rvMenu.setAdapter(new BaseRvAdapter<CateBean>(list) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new CateMenuAdapterItem(new OnItemSimpleClickListener<CateBean>() {
                    @Override
                    public void onItemClick(CateBean item) {
                        if (cateType == TYPE_MENU) {
                            setResult(RESULT_OK, new Intent().putExtra(Intents.CATE, item));
                            finish();
                        } else {
                            for (CateBean bean : list) {
                                bean.setCheck(false);
                            }
                            item.setCheck(true);
                            notifyDataSetChanged();
                            rvChild.scrollToPosition(childList.indexOf(item));
                        }
                    }
                });
            }
        });

        if (cateType == TYPE_DETAIL) {
            final GridLayoutManager layoutManager = new GridLayoutManager(context, 3);

            rvChild.setVisibility(View.VISIBLE);
            rvChild.setLayoutManager(layoutManager);
            rvChild.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Object object = childList.get(layoutManager.findFirstVisibleItemPosition());
                    if (object instanceof CateBean){
                        for (CateBean bean : list) {
                            bean.setCheck(false);
                        }
                        ((CateBean)object).setCheck(true);
                        rvMenu.getAdapter().notifyDataSetChanged();
                    }
                }
            });

            childList = new ArrayList<>();
            for (CateBean bean : list) {
                childList.add(bean);
                for (CateBean.ChildBean childBean : bean.getChild()) {
                    childList.add(childBean);
                }
            }

            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (childList.get(position) instanceof CateBean) {
                        return 3;
                    } else {
                        return 1;
                    }
                }
            });

            childAdapter = new BaseRvAdapter<Object>(childList) {
                @Override
                public Object getItemType(Object o) {
                    if (o instanceof CateBean){
                        return ITEM_TITLE;
                    } else {
                        return ITEM_CHILD;
                    }
                }

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    if (((int)type) == ITEM_TITLE){
                        return new CateTitleAdapterItem();
                    } else {
                        return new CateChildAdapterItem(new OnItemSimpleClickListener<CateBean.ChildBean>() {
                            @Override
                            public void onItemClick(CateBean.ChildBean item) {
                                setResult(RESULT_OK, new Intent().putExtra(Intents.CATE, new CateBean(item.getCateId(), item.getCateName())));
                                finish();
                            }
                        });
                    }
                }
            };

            rvChild.setAdapter(childAdapter);
        }
    }

    @Override
    protected void doBeforeFinish() {
        setResult(RESULT_CANCELED);
    }
}
