package jack.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;

import java.lang.ref.WeakReference;

/**
 * ================================================
 * description:
 * ================================================
 * package_name: jack.library.base
 * author: PayneJay
 * date: 2018/8/1.
 */

public abstract class BaseHolder<T> extends ViewHolder implements OnClickListener {
    protected View rootView;
    protected WeakReference<Activity> activity;
    protected Context context;

    public BaseHolder(View itemView) {
        super(itemView);
        this.rootView = itemView;
        itemView.setOnClickListener(this);
        this.onInitView();
    }

    /**
     * 提供的公共方法
     *
     * @param resId
     * @return
     */
    public <T extends View> T findViewById(int resId) {
        return (T) this.rootView.findViewById(resId);
    }

    public void Bind(BaseItemInfo baseItemInfo, WeakReference<Activity> activity) {
        if (activity != null) {
            Activity ac = activity.get();
            if (ac != null) {
                this.context = ac.getApplicationContext();
            }
        }
        this.activity = activity;
        if (baseItemInfo == null) {
            return;
        }
        this.onBind((T) baseItemInfo.getData());
    }

    /**
     * 获取当前 HOLDER  所在的 ACTIVITY  如果 activity 一经消失可能返回为空
     *
     * @return
     */
    public Activity getActivity() {
        if (this.activity != null) {
            Activity ac = this.activity.get();
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
        if (this.activity != null) {
            Activity ac = this.activity.get();
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
