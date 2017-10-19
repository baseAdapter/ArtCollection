package com.tsutsuku.artcollection.model.shopping;

/**
 * @Author Tsutsuku
 * @Create 2017/2/7
 * @Description
 */

public class ItemShoppingCartView {

    private boolean isVendor;
    private Object data;

    public ItemShoppingCartView(boolean isVendor, Object data) {
        this.isVendor = isVendor;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isVendor() {
        return isVendor;
    }

    public void setVendor(boolean vendor) {
        isVendor = vendor;
    }
}
