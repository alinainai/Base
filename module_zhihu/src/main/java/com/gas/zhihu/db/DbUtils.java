package com.gas.zhihu.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class DbUtils {

    private static final String DB_NAME= "zhihu_module.db";

    private DaoSession daoSession;

    private DbUtils() {}


    public static DbUtils getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder{
        private static final DbUtils sInstance = new DbUtils();
    }


    public void init(Application app){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(app, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


}
