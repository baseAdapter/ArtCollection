package com.tsutsuku.artcollection.model.shopping;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @Author Sun Renwei
 * @Create 2017/2/6
 * @Description 商品Bean，用于购物车和
 */

public class ItemVendor extends Observable implements Parcelable, Observer {

    /**
     * farmId : 86000001
     * farmName : 店铺一
     * products : [{"productId":"1","productAmount":"10","productName":"普通(体验版)","productPrice":"0.01","productBrief":"一次购买，四季采摘，超值体验！","productCover":"/upload/2016/06/27/20160627112105644.png","inventory":"835"}]
     */

    private String farmId;
    private String farmName;
    private List<ItemGoods> products;
    private InfoBean infoBean;
    private boolean checked;
    private boolean edit;


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            setChanged();
            notifyObservers('1');
        }
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        if (this.edit != edit) {
            this.edit = edit;
            setChanged();
            notifyObservers('2');
        }
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public List<ItemGoods> getProducts() {
        return products;
    }

    public void setProducts(List<ItemGoods> products) {
        this.products = products;
    }

    public InfoBean getInfoBean() {
        return infoBean;
    }

    public void setInfoBean(InfoBean infoBean) {
        this.infoBean = infoBean;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Boolean) {
            ItemGoods goods = (ItemGoods) o;
            if (goods.isChecked()) {
                boolean temp = true;
                for (ItemGoods item : this.products) {
                    temp &= item.isChecked();
                }
                if (temp) {
                    setChecked(true);
                }
            } else {
                setChecked(false);
            }
        }
    }

    public static class InfoBean implements Parcelable {

        /**
         * couponId : 0
         * couponFee : 0
         * delivery : [{"type":"1","des":"自提","fee":"0","free_total":"0"},{"type":"2","des":"送货上门(运费:5.00,满20.00免)","fee":"5.00","free_total":"20.00"}]
         */

        private String couponId;
        private String couponFee;
        private List<DeliveryBean> delivery;
        private DeliveryBean bean;
        private String userNote;
        private String totalPrice;
        private String totalNum;
        private String virtualBalance;
        private boolean usePoint;

        public String getVirtualBalance() {
            return virtualBalance;
        }

        public void setVirtualBalance(String virtualBalance) {
            this.virtualBalance = virtualBalance;
        }

        public boolean isUsePoint() {
            return usePoint;
        }

        public void setUsePoint(boolean usePoint) {
            this.usePoint = usePoint;
        }

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public DeliveryBean getBean() {
            return bean;
        }

        public void setBean(DeliveryBean bean) {
            this.bean = bean;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getCouponFee() {
            return couponFee;
        }

        public void setCouponFee(String couponFee) {
            this.couponFee = couponFee;
        }

        public List<DeliveryBean> getDelivery() {
            return delivery;
        }

        public void setDelivery(List<DeliveryBean> delivery) {
            this.delivery = delivery;
        }

        public String getUserNote() {
            return userNote == null ? "" : userNote;
        }

        public void setUserNote(String userNote) {
            this.userNote = userNote;
        }

        public static class DeliveryBean implements Parcelable{
            /**
             * type : 1
             * des : 自提
             * fee : 0
             * free_total : 0
             */

            private String type;
            private String des;
            private String fee;
            private String free_total;
            private boolean check;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getFree_total() {
                return free_total;
            }

            public void setFree_total(String free_total) {
                this.free_total = free_total;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.type);
                dest.writeString(this.des);
                dest.writeString(this.fee);
                dest.writeString(this.free_total);
                dest.writeByte(this.check ? (byte) 1 : (byte) 0);
            }

            public DeliveryBean() {
            }

            protected DeliveryBean(Parcel in) {
                this.type = in.readString();
                this.des = in.readString();
                this.fee = in.readString();
                this.free_total = in.readString();
                this.check = in.readByte() != 0;
            }

            public static final Creator<DeliveryBean> CREATOR = new Creator<DeliveryBean>() {
                @Override
                public DeliveryBean createFromParcel(Parcel source) {
                    return new DeliveryBean(source);
                }

                @Override
                public DeliveryBean[] newArray(int size) {
                    return new DeliveryBean[size];
                }
            };
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.couponId);
            dest.writeString(this.couponFee);
            dest.writeTypedList(this.delivery);
            dest.writeParcelable(this.bean, flags);
            dest.writeString(this.userNote);
            dest.writeString(this.totalPrice);
            dest.writeString(this.totalNum);
        }

        public InfoBean() {
        }

        protected InfoBean(Parcel in) {
            this.couponId = in.readString();
            this.couponFee = in.readString();
            this.delivery = in.createTypedArrayList(DeliveryBean.CREATOR);
            this.bean = in.readParcelable(DeliveryBean.class.getClassLoader());
            this.userNote = in.readString();
            this.totalPrice = in.readString();
            this.totalNum = in.readString();
        }

        public static final Creator<InfoBean> CREATOR = new Creator<InfoBean>() {
            @Override
            public InfoBean createFromParcel(Parcel source) {
                return new InfoBean(source);
            }

            @Override
            public InfoBean[] newArray(int size) {
                return new InfoBean[size];
            }
        };
    }


    public ItemVendor() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.farmId);
        dest.writeString(this.farmName);
        dest.writeTypedList(this.products);
        dest.writeParcelable(this.infoBean, flags);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.edit ? (byte) 1 : (byte) 0);
    }

    protected ItemVendor(Parcel in) {
        this.farmId = in.readString();
        this.farmName = in.readString();
        this.products = in.createTypedArrayList(ItemGoods.CREATOR);
        this.infoBean = in.readParcelable(InfoBean.class.getClassLoader());
        this.checked = in.readByte() != 0;
        this.edit = in.readByte() != 0;
    }

    public static final Creator<ItemVendor> CREATOR = new Creator<ItemVendor>() {
        @Override
        public ItemVendor createFromParcel(Parcel source) {
            return new ItemVendor(source);
        }

        @Override
        public ItemVendor[] newArray(int size) {
            return new ItemVendor[size];
        }
    };
}
