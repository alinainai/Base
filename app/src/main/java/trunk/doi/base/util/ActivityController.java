package trunk.doi.base.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

public class ActivityController {


    private static Stack<Activity> activityStack;
    private static ActivityController instance;

    private ActivityController() {
    }

    /**
     * 单一实例
     */
    public static ActivityController getActivityController() {
        if (instance == null) {
            instance = new ActivityController();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public  void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    public  void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    public  void closeAllActivity(Context context) {

        try {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
//            ActivityManager activityMgr = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
//            if(activityMgr != null){
//                activityMgr.killBackgroundProcesses(context.getApplicationContext().getPackageName());
//            }
//            System.exit(0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


}
