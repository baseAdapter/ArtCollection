package com.tsutsuku.artcollection.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/2/15
 * @Description 钱包信息缓存
 */

public class RecordsInfoBean implements Parcelable{


    /**
     * cashBalance : 9989.90
     * virtualBalance : 0.00
     * records : [{"billType":"order","cashAmount":"-10.00","virtualAmount":"0.00","outId":"17021317083608148","billTime":"2017-02-13 17:08:36","note":null},{"billType":"order","cashAmount":"-0.10","virtualAmount":"0.00","outId":"17021220191976066","billTime":"2017-02-12 20:19:19","note":null}]
     * withdrawMinMoney : 20
     */

    private String cashBalance;
    private String virtualBalance;
    private String withdrawMinMoney;
    private List<RecordsBean> records;

    public String getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(String cashBalance) {
        this.cashBalance = cashBalance;
    }

    public String getVirtualBalance() {
        return virtualBalance;
    }

    public void setVirtualBalance(String virtualBalance) {
        this.virtualBalance = virtualBalance;
    }

    public String getWithdrawMinMoney() {
        return withdrawMinMoney;
    }

    public void setWithdrawMinMoney(String withdrawMinMoney) {
        this.withdrawMinMoney = withdrawMinMoney;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean implements Parcelable{
        /**
         * billType : order
         * cashAmount : -10.00
         * virtualAmount : 0.00
         * outId : 17021317083608148
         * billTime : 2017-02-13 17:08:36
         * note : null
         */

        private String billType;
        private String cashAmount;
        private String virtualAmount;
        private String outId;
        private String billTime;
        private String note;

        public String getBillType() {
            return billType;
        }

        public void setBillType(String billType) {
            this.billType = billType;
        }

        public String getCashAmount() {
            return cashAmount;
        }

        public void setCashAmount(String cashAmount) {
            this.cashAmount = cashAmount;
        }

        public String getVirtualAmount() {
            return virtualAmount;
        }

        public void setVirtualAmount(String virtualAmount) {
            this.virtualAmount = virtualAmount;
        }

        public String getOutId() {
            return outId;
        }

        public void setOutId(String outId) {
            this.outId = outId;
        }

        public String getBillTime() {
            return billTime;
        }

        public void setBillTime(String billTime) {
            this.billTime = billTime;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.billType);
            dest.writeString(this.cashAmount);
            dest.writeString(this.virtualAmount);
            dest.writeString(this.outId);
            dest.writeString(this.billTime);
            dest.writeString(this.note);
        }

        public RecordsBean() {
        }

        protected RecordsBean(Parcel in) {
            this.billType = in.readString();
            this.cashAmount = in.readString();
            this.virtualAmount = in.readString();
            this.outId = in.readString();
            this.billTime = in.readString();
            this.note = in.readString();
        }

        public static final Creator<RecordsBean> CREATOR = new Creator<RecordsBean>() {
            @Override
            public RecordsBean createFromParcel(Parcel source) {
                return new RecordsBean(source);
            }

            @Override
            public RecordsBean[] newArray(int size) {
                return new RecordsBean[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cashBalance);
        dest.writeString(this.virtualBalance);
        dest.writeString(this.withdrawMinMoney);
        dest.writeList(this.records);
    }

    public RecordsInfoBean() {
    }

    protected RecordsInfoBean(Parcel in) {
        this.cashBalance = in.readString();
        this.virtualBalance = in.readString();
        this.withdrawMinMoney = in.readString();
        this.records = new ArrayList<RecordsBean>();
        in.readList(this.records, RecordsBean.class.getClassLoader());
    }

    public static final Creator<RecordsInfoBean> CREATOR = new Creator<RecordsInfoBean>() {
        @Override
        public RecordsInfoBean createFromParcel(Parcel source) {
            return new RecordsInfoBean(source);
        }

        @Override
        public RecordsInfoBean[] newArray(int size) {
            return new RecordsInfoBean[size];
        }
    };
}
