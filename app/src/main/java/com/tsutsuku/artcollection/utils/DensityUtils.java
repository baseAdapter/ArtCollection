/*
 *  Copyright (c) 2015 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */package com.tsutsuku.artcollection.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;


public class DensityUtils {

    private static float density = -1.0F;
    private static float scaledDensity = -1.0F;

    /**
     * px[像素]转化dp
     * @param px
     * @return
     */
    public static int px2dp(int px){
        return (int)(px / getDensity() + 0.5f);

    }

    /**
     * dip转化px[像素]
     * @param dp
     * @return
     */
    public static int dp2px(int dp){
        return (int)(dp * getDensity() + 0.5f);

    }

    /**
     * px[像素]转化sp
     * @param px
     * @return
     */
    public static int px2sp(int px) {
        return (int)(px / getScaledDensity() + 0.5f);
    }

    /**
     * sp[像素]转化px
     * @param sp
     * @return
     */
    public static int sp2px(int sp) {
        return (int)(sp * getScaledDensity() + 0.5f);
    }

    public static float getDensity() {
        if (density < 0.0F)
            density = Resources.getSystem().getDisplayMetrics().density;
        return density;
    }

    public static float getScaledDensity() {
        if (scaledDensity < 0)
            scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return scaledDensity;
    }

    /**
     * 获取屏幕宽度
     * @return 单位px
     */
    public static int getDisplayWidth(){
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return 单位px
     */
    public static int getDisplayHeight(){
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return dm.heightPixels;
    }
}
