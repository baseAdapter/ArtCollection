package com.tsutsuku.artcollection.model.point;

/**
 * @Author Sun Renwei
 * @Create 2017/2/27
 * @Description 兑换记录bean
 */

public class ItemPointRecord {


    /**
     * recordId : 198
     * integrateType : 2
     * integrateAmount : 34
     * outOrderId : 3
     * createTime : 2017-02-27 17:00:12
     * note :
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
