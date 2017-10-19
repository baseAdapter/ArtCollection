package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.model.shopping.ProvinceBean;
import com.tsutsuku.artcollection.presenter.address.ShoppingAddressDetailPresenterImpl;
import com.tsutsuku.artcollection.utils.SharedPref;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2017/1/19
 * @Description Content
 */

public class AreaDialog {
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.tlArea)
    TabLayout tlArea;
    @BindView(R.id.rvArea)
    RecyclerView rvArea;
    @BindView(R.id.tvArea)
    TextView tvArea;

    private ShoppingAddressDetailPresenterImpl presenter;
    private Context context;
    private DialogPlus dialog;
    private AreaAdapter adapter;
    private List<ProvinceBean> list;
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<ProvinceBean>>() {
    }.getType();

    private viewInterface baseView;

    public AreaDialog(Context context, ShoppingAddressDetailPresenterImpl presenter, viewInterface baseView) {
        this.presenter = presenter;
        this.context = context;
        this.baseView = baseView;
        initDialog();
    }

    private void initDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_area_list, null);
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvArea.setLayoutManager(layoutManager);

        list = gson.fromJson(SharedPref.getSysString(Constants.AREA_LIST), type);
        List<String> temp = new ArrayList<>();
        for (ProvinceBean bean : list) {
            temp.add(bean.getName());
        }

        adapter = new AreaAdapter(temp, context, new AreaAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (tlArea.getSelectedTabPosition()) {
                    case 0: {
                        for (int i = tlArea.getTabCount(); i > 1; i--) {
                            tlArea.removeTabAt(i - 1);
                        }
                        tlArea.getTabAt(0).setText(list.get(position).getName());
                        tlArea.addTab(tlArea.newTab().setText(context.getString(R.string.please_select)));
                        presenter.setCitys(list.get(position).getCitys());
                        tlArea.getTabAt(1).select();

                        adapter.notifyDataSetChanged();
                    }
                    break;
                    case 1: {
                        for (int i = tlArea.getTabCount(); i > 2; i--) {
                            tlArea.removeTabAt(i - 1);
                        }
                        tlArea.getTabAt(1).setText(presenter.getCitys().get(position).getName());
                        tlArea.addTab(tlArea.newTab().setText(context.getString(R.string.please_select)));
                        presenter.setAreas(presenter.getCitys().get(position).getArea());
                        tlArea.getTabAt(2).select();

                        adapter.notifyDataSetChanged();
                    }
                    break;
                    case 2: {
                        presenter.setArea(presenter.getAreas().get(position).getName());
                        presenter.setProvince(tlArea.getTabAt(0).getText().toString());
                        presenter.setCity(tlArea.getTabAt(1).getText().toString());

                        presenter.setAreaId(presenter.getAreas().get(position).getId());

                        baseView.setRegion(presenter.getProvince() + presenter.getCity() + presenter.getArea());
                        dialog.dismiss();
                    }
                    break;
                }
            }
        });
        rvArea.setAdapter(adapter);

        tlArea.addTab(tlArea.newTab().setText(context.getString(R.string.please_select)));
        tlArea.setTabTextColors(context.getResources().getColor(R.color.black), context.getResources().getColor(R.color.red));
        tlArea.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: {
                        List<String> data = adapter.getData();
                        data.clear();
                        for (ProvinceBean bean : list) {
                            data.add(bean.getName());
                        }
                        adapter.notifyDataSetChanged();
                    }
                    break;
                    case 1: {
                        List<String> data = adapter.getData();
                        data.clear();
                        for (ProvinceBean.CitysBean citysBean : presenter.getCitys()) {
                            data.add(citysBean.getName());
                        }
                        adapter.notifyDataSetChanged();
                    }
                    break;
                    case 2: {
                        List<String> data = adapter.getData();
                        data.clear();
                        for (ProvinceBean.CitysBean.AreaBean areaBean : presenter.getAreas()) {
                            data.add(areaBean.getName());
                        }
                        adapter.notifyDataSetChanged();
                    }
                    break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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

    public void setData(String province, String city, String area) {
        for (ProvinceBean provinceBean : list) {
            if (province.equals(provinceBean.getName())) {
            for (ProvinceBean.CitysBean cityBean : provinceBean.getCitys()) {
                if (city.equals(cityBean.getName())){
                for (ProvinceBean.CitysBean.AreaBean areaBean : cityBean.getArea()) {
                    if (area.equals(areaBean.getName())) {
                        tlArea.removeAllTabs();
                        tlArea.addTab(tlArea.newTab().setText(province));
                        tlArea.addTab(tlArea.newTab().setText(city));
                        tlArea.addTab(tlArea.newTab().setText(area));
                        presenter.setCitys(provinceBean.getCitys());
                        presenter.setAreas(cityBean.getArea());
                        presenter.setAreaId(areaBean.getId());

                        tlArea.getTabAt(2).select();
                        return;
                    }
                }}
            }}
        }
    }

    public void show() {
        dialog.show();
    }

    interface viewInterface {
        public void setRegion(String region);
    }
}
