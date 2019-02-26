package jack.myapplication.info;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import jack.myapplication.Constants;
import jack.myapplication.agent.AgentConfiguration;
import jack.myapplication.exception.AgentInitializationException;
import jack.myapplication.utils.SystemUtils;

public class AppManager implements Application.ActivityLifecycleCallbacks {
    private static AppManager instance;
    private ApplicationInformation applicationInformation;
    private AgentConfiguration agentConfiguration = new AgentConfiguration();
    private Application application;

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }

        return instance;
    }

    public void init(Application context) {
        this.application = context;
        try {
            initApplicationInformation();
        } catch (AgentInitializationException e) {
            e.printStackTrace();
        }
    }

    private void initApplicationInformation() throws AgentInitializationException {
        if (this.applicationInformation != null) {
            Log.d(Constants.TAG, "attempted to reinitialize ApplicationInformation.");
        } else {
            String packageName = this.application.getPackageName();
            PackageManager packageManager = this.application.getPackageManager();
            PackageInfo packageInfo = null;

            try {
                packageInfo = packageManager.getPackageInfo(packageName, 0);
            } catch (PackageManager.NameNotFoundException var9) {
                throw new AgentInitializationException("Could not determine package version: " + var9.getMessage());
            }

            String appVersion = this.agentConfiguration.getCustomApplicationVersion();
            if (TextUtils.isEmpty(appVersion)) {
                if (packageInfo == null || packageInfo.versionName == null || packageInfo.versionName.length() <= 0) {
                    throw new AgentInitializationException("Your app doesn't appear to have a version defined. Ensure you have defined 'versionName' in your manifest.");
                }

                appVersion = packageInfo.versionName;
            }

            Log.d(Constants.TAG, "Using application version " + appVersion);

            String appName;
            try {
                ApplicationInfo info = packageManager.getApplicationInfo(packageName, 0);
                if (info != null) {
                    appName = packageManager.getApplicationLabel(info).toString();
                } else {
                    appName = packageName;
                }
            } catch (PackageManager.NameNotFoundException var7) {
                Log.w(Constants.TAG, var7.toString());
                appName = packageName;
            } catch (SecurityException var8) {
                Log.w(Constants.TAG, var8.toString());
                appName = packageName;
            }

            Log.d(Constants.TAG, "Using application name " + appName);
            String build = this.agentConfiguration.getCustomBuildIdentifier();
            if (TextUtils.isEmpty(build)) {
                if (packageInfo != null) {
                    build = String.valueOf(packageInfo.versionCode);
                } else {
                    build = "";
                    Log.w(Constants.TAG, "Your app doesn't appear to have a version code defined. Ensure you have defined 'versionCode' in your manifest.");
                }
            }

            Log.d(Constants.TAG, "Using build " + build);
            this.applicationInformation = new ApplicationInformation(appName, appVersion, packageName, build);
            this.applicationInformation.setVersionCode(packageInfo.versionCode);

            showDeviceInfo();
            AppActivityLifecycleManager.getInstance((Application) application);
        }
    }

    private void showDeviceInfo() {
        Log.e(Constants.TAG,
                "系统版本:" + SystemUtils.getSystemVersion() +
                        "\n手机型号:" + SystemUtils.getSystemModel() +
                        "\n系统语言:" + SystemUtils.getSystemLanguage() +
                        "\n手机厂商:" + SystemUtils.getDeviceBrand() +
                        "\n当前日期时间:" + SystemUtils.getDateAndTime(application) +
                        "\n当前时区:" + SystemUtils.getTimeZone(application) +
                        "\n手机型号:" + SystemUtils.getPhoneModel(application) +
                        "\n手机品牌:" + SystemUtils.getPhoneProduct(application) +
                        "\n网络状况:" + SystemUtils.isNetworkAvailable(application) +
                        "\n网络类型:" + SystemUtils.getNetWorkType(application) +
                        "\n运营商编号:" + SystemUtils.getNetworkOperator(application) +
                        "\nsim卡所在国家:" + SystemUtils.getNetworkCountryIso(application) +
                        "\n运营商名称:" + SystemUtils.getNetworkOperatorName(application)
        );
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
