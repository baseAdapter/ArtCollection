package com.tsutsuku.artcollection.ui.exchange;


/***
 *
 * scale : 0.02 比例
 * integrate_total:所拥有的积分数量
 * gold_total:所拥有的金币数量
 *
 ***/


public class IntegrateExchangeGold {
    private String scale;
    private String integrate_total;
    private String gold_total;

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getIntegrate_total() {
        return integrate_total;
    }

    public void setIntegrate_total(String integrate_total) {
        this.integrate_total = integrate_total;
    }

    public String getGold_total() {
        return gold_total;
    }

    public void setGold_total(String gold_total) {
        this.gold_total = gold_total;
    }
}
