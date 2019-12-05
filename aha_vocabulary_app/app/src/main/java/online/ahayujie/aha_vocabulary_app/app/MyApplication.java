package online.ahayujie.aha_vocabulary_app.app;

import android.content.Context;

import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.utils.KLog;
import online.ahayujie.aha_vocabulary_app.BuildConfig;

/**
 * @author aha
 */
public class MyApplication extends BaseApplication {

    public static final String TAG = "ahayujietag";

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.DEBUG);
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
