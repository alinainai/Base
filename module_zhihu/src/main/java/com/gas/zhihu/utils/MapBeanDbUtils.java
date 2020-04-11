package com.gas.zhihu.utils;

import com.gas.zhihu.bean.MapBean;
import com.gas.zhihu.db.DaoSession;
import com.gas.zhihu.db.DbUtils;
import com.gas.zhihu.db.MapBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class MapBeanDbUtils {


    public static void insertMapBean(MapBean bean) {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        MapBeanDao dao = daoSession.getMapBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        List list = qb.where(MapBeanDao.Properties.KeyName.eq(bean.getKeyName())).list();
        if (list.size() > 0) {
        } else {
            dao.insert(bean);
        }

    }

    @SuppressWarnings("unchecked")
    public static void delete(String keyName) {
        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        MapBeanDao dao = daoSession.getMapBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        ArrayList<MapBean> list = (ArrayList<MapBean>) qb.where(MapBeanDao.Properties.KeyName.eq(keyName)).list();
        if (list != null && !list.isEmpty()) {
            dao.delete(list.get(0));
        }
    }


    @SuppressWarnings("unchecked")
    public static MapBean queryData(String keyName) {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        MapBeanDao dao = daoSession.getMapBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        ArrayList<MapBean> list = (ArrayList<MapBean>) qb.where(MapBeanDao.Properties.KeyName.eq(keyName)).list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static List<MapBean> queryAllMapData() {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        MapBeanDao dao = daoSession.getMapBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        ArrayList<MapBean> list = (ArrayList<MapBean>) qb.list();
        if (list != null && !list.isEmpty()) {
            return list;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static boolean addComment(String mapKey,String comment) {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        MapBeanDao dao = daoSession.getMapBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        MapBean bean = (MapBean) qb.where(MapBeanDao.Properties.KeyName.eq(mapKey)).unique();
        if(bean!=null){
            bean.setNote(comment);
            dao.update(bean);
            return true;
        }
        return false;

    }

    @SuppressWarnings("unchecked")
    public static void deleteAll() {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        MapBeanDao dao = daoSession.getMapBeanDao();
        if(dao!=null){
            dao.deleteAll();
        }

    }


}
