package com.tsutsuku.artcollection.ui.adapter;

import java.util.HashMap;

/**
 * @Author Sun Renwei
 * @Create 2017/1/11
 * @Description Content
 */

public class ItemTypeUtil {
    private HashMap<Object, Integer> typePool;

    public void setTypePool(HashMap<Object, Integer> typePool) {
        this.typePool = typePool;
    }

    public int getIntType(Object type) {
        if (typePool == null) {
            typePool = new HashMap<>();
        }

        if (!typePool.containsKey(type)) {
            typePool.put(type, typePool.size());
        }
        return typePool.get(type);
    }
}
