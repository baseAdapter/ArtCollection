package com.tsutsuku.artcollection.ui.exchange;

/**
 * Created by Administrator on 2017/10/8.
 */

public class CoinDetail {

    /**
     *
     * gold_amount：获得的金币数
     * create_time: 获得的时间
     * note: 何种方式
     *
     **/

    private String goldNums;
    private String createTime;
    private String note;


    public String getGoldNums() {
        return goldNums;
    }

    public void setGoldNums(String goldNums) {
        this.goldNums = goldNums;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
