package jack.myapplication.info;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import jack.myapplication.Constants;
import jack.myapplication.http.Sender;

public class AppActivityLifecycleManager implements Application.ActivityLifecycleCallbacks {
    private static AppActivityLifecycleManager mBaseLifecycle;
    private int refCount = 0;

    public static AppActivityLifecycleManager getInstance(Application application) {
        if (null == mBaseLifecycle) {
            mBaseLifecycle = new AppActivityLifecycleManager();
            application.registerActivityLifecycleCallbacks(mBaseLifecycle);
        }
        return mBaseLifecycle;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        refCount++;
        Log.e(Constants.TAG, "App在前台……onActivityStarted");
        Sender.getInstance().sendRequest(Constants.GET);
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        refCount--;
        if (refCount == 0) {
            Log.e(Constants.TAG, "App在后台……");
            Sender.getInstance().sendRequest(Constants.POST);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
