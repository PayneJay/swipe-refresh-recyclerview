package jack.myapplication;

import android.app.Application;

import jack.myapplication.info.AppManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.getInstance().init(this);
    }
}
