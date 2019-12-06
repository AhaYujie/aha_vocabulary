package online.ahayujie.aha_vocabulary_app.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Vector;

import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.utils.KLog;
import okhttp3.Interceptor;
import okhttp3.Response;
import online.ahayujie.aha_vocabulary_app.BuildConfig;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.ui.BaseAbstractActivity;
import online.ahayujie.aha_vocabulary_app.ui.login.LoginActivity;

/**
 * @author aha
 */
public class MyApplication extends BaseApplication {

    public static final String TAG = "ahayujietag";

    private static Context context;

    private static Interceptor authenticationInterceptor;

    private static Vector<Activity> activities = new Vector<>();

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.DEBUG);
        context = getApplicationContext();
        initAuthenticationInterceptor();
    }

    /**
     * 初始化登录拦截器
     */
    private void initAuthenticationInterceptor() {
        authenticationInterceptor = new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                if (response.code() != 401) {
                    return response;
                }
                Log.d(TAG, "未登录，跳转到登陆界面");
                final BaseAbstractActivity activity = (BaseAbstractActivity) activities
                        .get(activities.size() - 1);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.showLoginDialog("登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finishAllActivities();
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
                    }
                });
                return response;
            }
        };
    }

    public static void addActivity(Activity activity) {
        Log.d(TAG, "add : " + activity);
        activities.add(activity);
    }

    public synchronized static void finishAllActivities() {
        while (!activities.isEmpty()) {
            Log.d(TAG, "finish: " + activities.get(0).toString());
            activities.get(0).finish();
            activities.remove(0);
        }
    }

    public static void deleteActivity(Activity activity) {
        Log.d(TAG, "delete: " + activity);
        activities.remove(activity);
    }

    public static Context getContext() {
        return context;
    }

    public static Interceptor getAuthenticationInterceptor() {
        return authenticationInterceptor;
    }
}
