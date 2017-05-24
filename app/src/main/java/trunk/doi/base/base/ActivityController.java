package trunk.doi.base.base;

import android.app.Activity;
import android.os.Process;

import java.util.LinkedList;

public class ActivityController {
	
	private static LinkedList<Activity> activities = new LinkedList<Activity>();

	public  static void addActivity(Activity activity){
		
		activities.add(activity);
	}
	public  static void removeActivity(Activity activity){
		
		activities.remove(activity);
	}
	public  static void closeAllActivity(){
		
		for (Activity activity:activities ) {
			activity.finish();
		}
		Process.killProcess(Process.myPid());
	}
	
	
}
