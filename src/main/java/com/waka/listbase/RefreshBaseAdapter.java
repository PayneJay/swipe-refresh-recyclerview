package com.waka.listbase;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nayibo on 2017/7/27.
 */

public abstract class RefreshBaseAdapter extends RecyclerView.Adapter<BaseHolder> {
    protected List<BaseItemInfo> datas = new ArrayList<>();
    protected LayoutInflater inflate ;
    protected WeakReference<Activity> activity ;

    public RefreshBaseAdapter(Activity activity) {
        inflate = LayoutInflater.from(activity);
        this.activity = new WeakReference<>(activity) ;
    }

    public void setData(List<BaseItemInfo> datas){
        this.datas = new ArrayList<>(datas) ;
    }

    public List<BaseItemInfo> getDatas() {
        return datas;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        holder.Bind(getItem(position), activity);
    }

    public Activity getActivity(){
        return activity.get() ;
    }

    public boolean isFinishing(){
        Activity activity = getActivity() ;
        return activity == null || activity.isFinishing();
    }

    @Override
    public int getItemCount() {
        if(datas != null){
            return datas.size() ;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        int type = BaseItemInfo.DEFAULT_VIEW_TYPE ;
        BaseItemInfo info = getItem(position) ;
        if(info != null)
            type = info.getViewType() ;
        return type;
    }

    public BaseItemInfo getItem(int position){
        if(position < 0 || position >= datas.size()){
            return null ;
        }
        return datas.get(position) ;
    }
}
