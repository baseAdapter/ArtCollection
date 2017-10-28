package com.tsutsuku.artcollection.ui.exchange;

/**
 * Created by Administrator on 2017/10/28.
 */

public class IntegrationDetail {


    /**
     * recordId : 390
     * integrateType : 1
     * integrateAmount : 1000
     * outOrderId : 364
     * createTime : 2017-10-20 14:52:10
     * note : 注册送积分
     */

    private String recordId;
    private String integrateType;
    private String integrateAmount;
    private String outOrderId;
    private String createTime;
    private String note;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getIntegrateType() {
        return integrateType;
    }

    public void setIntegrateType(String integrateType) {
        this.integrateType = integrateType;
    }

    public String getIntegrateAmount() {
        return integrateAmount;
    }

    public void setIntegrateAmount(String integrateAmount) {
        this.integrateAmount = integrateAmount;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
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
