package com.waka.listbase;

/**
 * Created by nayibo on 2017/7/27.
 */

public abstract class BaseItemInfo<T> {

    public static final int DEFAULT_VIEW_TYPE = -1;

    private T data;

    /**
     * 获取类型
     *
     * @return
     */
    public abstract int getViewType();

    /**
     * 获取对应的数据结构体
     *
     * @return
     */
    public T getData() {
        return data;
    }

    /**
     * 设置对应的结构体
     *
     * @param netBean
     */
    public void setData(T netBean) {
        data = netBean;
    }
}