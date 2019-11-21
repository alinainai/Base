package com.base.lib.base;

/**
 * ================================================
 * Created by JessYan on 2018/7/27 15:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class Platform {
    public static final boolean DEPENDENCY_AUTO_LAYOUT;
    public static final boolean DEPENDENCY_SUPPORT_DESIGN;
    public static final boolean DEPENDENCY_GLIDE;
    public static final boolean DEPENDENCY_ANDROID_EVENTBUS;
    public static final boolean DEPENDENCY_EVENTBUS;

    static {
        DEPENDENCY_AUTO_LAYOUT = findClassByClassName("com.zhy.autolayout.AutoLayoutInfo");
        DEPENDENCY_SUPPORT_DESIGN = findClassByClassName("com.google.android.material.snackbar.Snackbar");
        DEPENDENCY_GLIDE = findClassByClassName("com.bumptech.glide.Glide");
        DEPENDENCY_ANDROID_EVENTBUS = findClassByClassName("org.simple.eventbus.EventBus");
        DEPENDENCY_EVENTBUS = findClassByClassName("org.greenrobot.eventbus.EventBus");
    }

    private static boolean findClassByClassName(String className) {
        boolean hasDependency;
        try {
            Class.forName(className);
            hasDependency = true;
        } catch (ClassNotFoundException e) {
            hasDependency = false;
        }
        return hasDependency;
    }
}
