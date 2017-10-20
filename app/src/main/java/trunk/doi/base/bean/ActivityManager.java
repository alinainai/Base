package trunk.doi.base.bean;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * 作者：Mr.Lee on 2017-9-18 17:31
 * 邮箱：569932357@qq.com
 */

public class ActivityManager {

    private HashMap<String, WeakReference<Activity>> activities = new HashMap<>();
    private static ActivityManager activityManager;

    private ActivityManager() {
    }


    public static ActivityManager getInstance() {
        if (null == activityManager) {
            activityManager = new ActivityManager();
        }
        return activityManager;
    }

    public void addActivity(Activity activity) {
        if (null != activity) {
            activities.put(activity.getClass().getSimpleName(), new WeakReference<>(activity));
        }
    }

    public void exitApp() {

        for (String key : activities.keySet()) {
            WeakReference<Activity> weakReference = activities.get(key);
            if (null != weakReference && weakReference.get() != null) {
                weakReference.get().finish();
            }
        }
    }

    public static boolean isContextInvalid(final Context context) {
        return context == null || ((Activity) context).isFinishing();
    }
}
