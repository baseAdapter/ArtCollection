package com.tsutsuku.artcollection.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/2/28
 * @Description
 */

public class CateBean implements Parcelable {

    /**
     * cateId : 7
     * cateName : 书画
     * child : [{"cateId":"7","cateName":"所有"}]
     */

    private String cateId;
    private String cateName;
    private String photo;
    private boolean check;
    private List<ChildBean> child;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public CateBean(String cateId, String cateName) {
        this.cateId = cateId;
        this.cateName = cateName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CateBean) {
            if (((CateBean) obj).getCateId().equals(getCateId())) {
                return true;
            }
        }
        return false;
    }

    public static class ChildBean implements Parcelable {
        /**
         * cateId : 7
         * cateName : 所有
         */

        private String cateId;
        private String cateName;
        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getCateId() {
            return cateId;
        }

        public void setCateId(String cateId) {
            this.cateId = cateId;
        }

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cateId);
            dest.writeString(this.cateName);
        }

        public ChildBean() {
        }

        protected ChildBean(Parcel in) {
            this.cateId = in.readString();
            this.cateName = in.readString();
        }

        public static final Creator<ChildBean> CREATOR = new Creator<ChildBean>() {
            @Override
            public ChildBean createFromParcel(Parcel source) {
                return new ChildBean(source);
            }

            @Override
            public ChildBean[] newArray(int size) {
                return new ChildBean[size];
            }
        };

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ChildBean) {
                if (((ChildBean) obj).getCateId().equals(getCateId())) {
                    return true;
                }
            }
            return false;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cateId);
        dest.writeString(this.cateName);
        dest.writeList(this.child);
    }

    public CateBean() {
    }

    protected CateBean(Parcel in) {
        this.cateId = in.readString();
        this.cateName = in.readString();
        this.child = new ArrayList<ChildBean>();
        in.readList(this.child, ChildBean.class.getClassLoader());
    }

    public static final Creator<CateBean> CREATOR = new Creator<CateBean>() {
        @Override
        public CateBean createFromParcel(Parcel source) {
            return new CateBean(source);
        }

        @Override
        public CateBean[] newArray(int size) {
            return new CateBean[size];
        }
    };
}
