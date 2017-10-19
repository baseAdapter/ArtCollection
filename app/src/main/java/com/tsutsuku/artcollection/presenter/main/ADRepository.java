package com.tsutsuku.artcollection.presenter.main;

import android.content.Context;

import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.other.custom.ui.CustomDetailActivity;
import com.tsutsuku.artcollection.other.pawn.ui.CrowdDetailActivity;
import com.tsutsuku.artcollection.other.rent.ui.RentDetailActivity;
import com.tsutsuku.artcollection.ui.activity.ActivityDetailActivity;
import com.tsutsuku.artcollection.ui.auction.AuctionDetailActivity;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.ui.lesson.LessonDetailActivity;
import com.tsutsuku.artcollection.ui.product.ProductDetailActivity;
import com.tsutsuku.artcollection.ui.shopping.VendorDetailActivity;

/**
 * @Author Sun Renwei
 * @Create 2017/3/2
 * @Description 广告解析类
 */

public class ADRepository {
    public static void parseAD(Context context, HomeBean.ADBean bean) {
        switch (Integer.valueOf(bean.getItemType())) {
            case 1: {// web
                WebActivity.launch(context, bean.getOutLinkOrId());
            }
            break;
            case 2: {// merchant
                VendorDetailActivity.launch(context, bean.getOutLinkOrId());
            }
            break;
            case 3: {// goods
                ProductDetailActivity.launch(context, bean.getOutLinkOrId());
            }
            break;
            case 4: {// lectures
                LessonDetailActivity.launch(context, bean.getOutLinkOrId());
            }
            break;
            case 5: {// activity
                ActivityDetailActivity.launch(context, bean.getOutLinkOrId());
            }
            break;
            case 6: {// Auction
                AuctionDetailActivity.launch(context, bean.getOutLinkOrId());
            }
            break;
            case 7: {// News
                WebActivity.launch(context, bean.getOutLinkOrId());
            }
            break;
            case 8: {// Custom
                CustomDetailActivity.launch(context, bean.getOutLinkOrId());
            }
            break;
            case 9:{// Crowd
                CrowdDetailActivity.launch(context, bean.getOutLinkOrId());
            }
            break;
            case 10:{//Rent
                RentDetailActivity.launch(context, bean.getOutLinkOrId());
            }
            break;
        }
    }
}
