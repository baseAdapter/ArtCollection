package com.tsutsuku.artcollection.model.shopping;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author Sun Renwei
 * @Create 2017/1/18
 * @Description Content
 */

public class ItemAddress implements Parcelable {


    /**
     * addressId : 1000058
     * consigneeName : 胡文波
     * contactNumber : 13588065321
     * province :
     * city :
     * county :
     * sex : 先生
     * detailAddress : 延安路42号
     * isDefault : 0
     */

    private String addressId;
    private String consigneeName;
    private String contactNumber;
    private String province;
    private String city;
    private String county;
    private String sex;
    private String detailAddress;
    private String isDefault;
    private String address;

    public String getAddress() {
        return address == null ? province + city + county + detailAddress : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.addressId);
        dest.writeString(this.consigneeName);
        dest.writeString(this.contactNumber);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.county);
        dest.writeString(this.sex);
        dest.writeString(this.detailAddress);
        dest.writeString(this.isDefault);
        dest.writeString(this.address);
    }

    public ItemAddress() {
    }

    protected ItemAddress(Parcel in) {
        this.addressId = in.readString();
        this.consigneeName = in.readString();
        this.contactNumber = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.county = in.readString();
        this.sex = in.readString();
        this.detailAddress = in.readString();
        this.isDefault = in.readString();
        this.address = in.readString();
    }

    public static final Creator<ItemAddress> CREATOR = new Creator<ItemAddress>() {
        @Override
        public ItemAddress createFromParcel(Parcel source) {
            return new ItemAddress(source);
        }

        @Override
        public ItemAddress[] newArray(int size) {
            return new ItemAddress[size];
        }
    };
}
