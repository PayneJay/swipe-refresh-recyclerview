package com.waka.listbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by nayibo on 2017/7/27.
 */

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected View rootView;
    protected WeakReference<Activity> activity;
    protected Context context;

    public BaseHolder(View itemView) {
        super(itemView);
        rootView = itemView;
        itemView.setOnClickListener(this);
        onInitView();
    }

    /**
     * 提供的公共方法
     *
     * @param resId
     * @return
     */
    public <T extends View> T findViewById(int resId) {
        return (T) rootView.findViewById(resId);
    }

    public void Bind(BaseItemInfo baseItemInfo, WeakReference<Activity> activity) {
        if (activity != null) {
            Activity ac = activity.get();
            if (ac != null) {
                context = ac.getApplicationContext();
            }
        }
        this.activity = activity;
        if (baseItemInfo == null) {
            return;
        }
        onBind((T) baseItemInfo.getData());
    }

    /**
     * 获取当前 HOLDER  所在的 ACTIVITY  如果 activity 一经消失可能返回为空
     *
     * @return
     */
    public Activity getActivity() {
        if (activity != null) {
            Activity ac = activity.get();
            return ac;
        }
        return null;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    /**
     * 获取当前的页面是否存在
     *
     * @return
     */
    public boolean isFinishing() {
        if (activity != null) {
            Activity ac = activity.get();
            if (ac != null)
                return ac.isFinishing();
        } else {
            return true;
        }
        return true;
    }


    /**
     * 初始化view
     * 同样的类型只调用一次
     */
    public abstract void onInitView();

    /**
     * 显示view
     * 每次显示都调用
     */

    public abstract void onBind(T t);

    @Override
    public void onClick(View v) {

    }
}
