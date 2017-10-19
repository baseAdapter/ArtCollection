package com.tsutsuku.artcollection.presenter.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.HomeContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.BusBean;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.model.ItemProduct;
import com.tsutsuku.artcollection.model.activity.ItemActivity;
import com.tsutsuku.artcollection.model.lesson.ItemVideo;
import com.tsutsuku.artcollection.other.custom.ui.CustomHomeActivity;
import com.tsutsuku.artcollection.other.pawn.ui.PawnHomeActivity;
import com.tsutsuku.artcollection.ui.activity.ActivityAdapterItem;
import com.tsutsuku.artcollection.ui.activity.ActivityDetailActivity;
import com.tsutsuku.artcollection.ui.activity.ActivityListActivity;
import com.tsutsuku.artcollection.ui.activity.LiveListActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.identify.IdentifyActivity;
import com.tsutsuku.artcollection.ui.lesson.LessonActivity;
import com.tsutsuku.artcollection.ui.lesson.LessonDetailActivity;
import com.tsutsuku.artcollection.ui.main.HomeHeaderView;
import com.tsutsuku.artcollection.ui.main.HomeLessonAdapterItem;
import com.tsutsuku.artcollection.ui.main.HomeLiveAdapterItem;
import com.tsutsuku.artcollection.ui.main.HomeMenuAdapterItem;
import com.tsutsuku.artcollection.ui.main.HomeProductAdapterItem;
import com.tsutsuku.artcollection.ui.main.HomeTitleAdapterItem;
import com.tsutsuku.artcollection.ui.main.ProductSearchActivity;
import com.tsutsuku.artcollection.ui.news.NewsListActivity;
import com.tsutsuku.artcollection.ui.product.ProductActivity;
import com.tsutsuku.artcollection.ui.shopping.VendorListActivity;
import com.tsutsuku.artcollection.ui.utils.RcvAdapterWrapper;
import com.tsutsuku.artcollection.utils.RxBus;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by sunrenwei on 2017/01/04
 * 创建处理逻辑的类
 *
 */

public class HomePresenterImpl implements HomeContract.Presenter {
    public static final String TAG = HomePresenterImpl.class.getSimpleName();

    private static final int ITEM_LESSON = 1;
    private static final int ITEM_ACTIVITY = 2;
    private static final int ITEM_LIVE = 3;
    private static final int ITEM_PRODUCT = 4;
    private static final int ITEM_TITLE = 5;
    private HomeContract.View view;

    private RcvAdapterWrapper wrapper;
    private HomeHeaderView headerView;
    private BaseRvAdapter menuAdapter;

    private GridLayoutManager layoutManager;
    private Context context;
    private Gson gson = new Gson();
    private Type homeBeanType = new TypeToken<HomeBean>() {
    }.getType();

    private String titles[] = {"人气讲堂", "热门活动", "精彩直播", "最新推荐"};
    private BaseRvAdapter adapter;

    public HomePresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void initView(final RecyclerView rvBase) {

        adapter = new BaseRvAdapter<Object>(null) {

            @Override
            public void setTotal(int total) {
                this.total = total;
            }

            @Override
            public Object getItemType(Object o) {
                if (o instanceof String) {
                    return ITEM_TITLE;
                } else if (o instanceof ItemVideo) {
                    return ITEM_LESSON;
                } else if (o instanceof ItemActivity) {
                    if (((ItemActivity) o).isLive()) {
                        return ITEM_LIVE;
                    } else {
                        return ITEM_ACTIVITY;
                    }
                } else {
                    return ITEM_PRODUCT;
                }
            }

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                switch ((int) type) {
                    case ITEM_TITLE: {
                        return new HomeTitleAdapterItem();
                    }
                    case ITEM_LESSON: {
                        return new HomeLessonAdapterItem(new OnItemSimpleClickListener<ItemVideo>() {
                            @Override
                            public void onItemClick(ItemVideo item) {
                                LessonDetailActivity.launch(context, item.getVideoId());
                            }
                        });
                    }
                    case ITEM_ACTIVITY: {
                        return new ActivityAdapterItem(context, new OnItemSimpleClickListener<ItemActivity>() {
                            @Override
                            public void onItemClick(ItemActivity item) {
                                ActivityDetailActivity.launch(context, item.getActivityId());
                            }
                        });
                    }
                    case ITEM_LIVE: {
                        return new HomeLiveAdapterItem();
                    }
                    default: {
                        return new HomeProductAdapterItem(context);
                    }
                }
            }
        };

        menuAdapter = new BaseRvAdapter<HomeBean.MenusBean>(null) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new HomeMenuAdapterItem(context, new OnItemSimpleClickListener<HomeBean.MenusBean>() {
                    @Override
                    public void onItemClick(HomeBean.MenusBean item) {
                        switch (item.getKeyWord()) {
                            case "101": {//拍卖
                                RxBus.getDefault().post(BusEvent.HOME_UI, new BusBean(BusEvent.HomeUI.AUCTION));
                            }
                            break;
                            case "102": {//活动
                                ActivityListActivity.launch(context);
                            }
                            break;
                            case "103": {//讲堂
                                LessonActivity.launch(context);
                            }
                            break;
                            case "104": {//直播
                                LiveListActivity.launch(context);
                            }
                            break;
                            case "105": {//商家
                                VendorListActivity.launch(context);
                            }
                            break;
                            case "106": {//鉴定
                                IdentifyActivity.launch(context);
                            }
                            break;
                            case "107": {//资讯
                                NewsListActivity.launch(context);
                            }
                            break;
                            case "108": {//宝贝
                                ProductActivity.launch(context);
                            }
                            break;
                            case "109": {//分类
                                ProductSearchActivity.launch(context);
                            }
                            break;
                            case "120": {//个性定制
                                CustomHomeActivity.launch(context);
                            }
                            break;
                            case "121": {//典当
                                PawnHomeActivity.launch(context);
                            }
                            break;
                            default: {
                            }
                            break;
                        }
                    }
                });
            }
        };

        final GridLayoutManager layoutManager = new GridLayoutManager(context, 6);
        headerView = new HomeHeaderView(context, menuAdapter);
        rvBase.setLayoutManager(layoutManager);
        wrapper = new RcvAdapterWrapper(adapter, layoutManager);
        wrapper.setHeaderView(headerView.getView());
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getData().isEmpty()) {
                    return layoutManager.getSpanCount();
                }
                if (position == 0) {
                    return 6;
                } else {
                    switch ((int) adapter.getItemType(adapter.getData().get(position - 1))) {
                        case ITEM_TITLE:
                            return 6;
                        case ITEM_LESSON:
                            return 3;
                        case ITEM_ACTIVITY:
                            return 6;
                        case ITEM_LIVE:
                            return 2;
                        case ITEM_PRODUCT:
                            return 3;
                        default:
                            return 6;
                    }
                }
            }
        });
        rvBase.setAdapter(wrapper);

        adapter.setupScroll(rvBase, new BaseRvAdapter.CallBack() {
            @Override
            public int findLastVisibleItemPosition() {
                return layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void loadData() {
                getData(false);
            }
        });
    }

    @Override
    public void loadData() {
        getData(false);
    }

    @Override
    public void refreshData() {
        getAD();
    }

    private void getData(final boolean refresh) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.getProductsList");
        hashMap.put("isAuction", "2");
        hashMap.put("top", "1");
        hashMap.put("pageIndex", refresh ? adapter.clearPageIndex() : adapter.addPageIndex());
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if (refresh) {
                        adapter.setTotal(data.getJSONObject("list").getInt("total"));
                    }
                    adapter.addData(data.getJSONObject("list").getString("list"), new TypeToken<List<ItemProduct>>() {
                    }.getType());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }

            @Override
            protected void onFinish() {
                adapter.finishLoading();
            }
        });
    }

    private void getAD() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "App.getIndexAdMenusV2");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    HomeBean bean = gson.fromJson(data.getString("list"), homeBeanType);
                    headerView.setData(bean);

                    List<Object> list = new ArrayList<>();
                    list.add(titles[0]);
                    list.addAll(bean.getVideo_list());
                    list.add(titles[1]);
                    list.addAll(bean.getActivit_list());
                    list.add(titles[2]);
                    for (ItemActivity item : bean.getVcloud_list()) {
                        item.setLive(true);
                    }
                    list.addAll(bean.getVcloud_list());
                    list.add(titles[3]);
                    adapter.setData(list);

                    getData(true);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}