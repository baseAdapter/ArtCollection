package com.tsutsuku.artcollection.model.live;

/**
 * @Author Tsutsuku
 * @Create 2017/3/19
 * @Description 直播状态
 */

public class LiveStatusInfo {

    /**
     * needRecord : 0
     * uid : 69850
     * duration : 120
     * status : 0
     * name : 直播对接测试了
     * filename : 今年最好的年礼，只在库拍
     * format : 1
     * type : 0
     * ctime : 1489460333285
     * cid : 24181ebafa704b21ab973dfd77e2d3a9
     * recordStatus : null
     */

    private int needRecord;
    private String uid;
    private int duration;
    private int status;
    private String name;
    private String filename;
    private int format;
    private int type;
    private long ctime;
    private String cid;
    private Object recordStatus;

    public int getNeedRecord() {
        return needRecord;
    }

    public void setNeedRecord(int needRecord) {
        this.needRecord = needRecord;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Object getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Object recordStatus) {
        this.recordStatus = recordStatus;
    }
}
