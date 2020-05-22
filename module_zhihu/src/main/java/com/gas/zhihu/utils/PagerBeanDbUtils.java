package com.gas.zhihu.utils;

import com.gas.zhihu.bean.PaperBean;
import com.gas.zhihu.db.DaoSession;
import com.gas.zhihu.db.DbUtils;
import com.gas.zhihu.db.PaperBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PagerBeanDbUtils {


    @SuppressWarnings("unchecked")
    public static void insertMapBean(PaperBean bean) {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        PaperBeanDao dao = daoSession.getPaperBeanDao();
        QueryBuilder<PaperBean> qb = dao.queryBuilder();
        List<PaperBean> list =  qb.where(PaperBeanDao.Properties.PathName.eq(bean.getPathName()),
                PaperBeanDao.Properties.VoltageLevel.eq(bean.getVoltageLevel()),
                PaperBeanDao.Properties.MapKey.eq(bean.getMapKey()),
                PaperBeanDao.Properties.FileName.eq(bean.getFileName())).list();
        if (list.size() > 0) {
        } else {
            dao.insert(bean);
        }

    }

    @SuppressWarnings("unchecked")
    public static PaperBean queryData(PaperBean bean) {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        PaperBeanDao dao = daoSession.getPaperBeanDao();
        QueryBuilder qb = dao.queryBuilder();
       return (PaperBean)qb.where(PaperBeanDao.Properties.PathName.eq(bean.getPathName()),
                PaperBeanDao.Properties.VoltageLevel.eq(bean.getVoltageLevel()),
                PaperBeanDao.Properties.MapKey.eq(bean.getMapKey()),
                PaperBeanDao.Properties.FileName.eq(bean.getFileName())).unique();

    }

    @SuppressWarnings("unchecked")
    public static void delete(String path) {
        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        PaperBeanDao dao = daoSession.getPaperBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        List<PaperBean> list = (ArrayList<PaperBean>) qb.where(PaperBeanDao.Properties.PathName.eq(path)).list();
        if (list != null && !list.isEmpty()) {
            dao.delete(list.get(0));
        }
    }


    @SuppressWarnings("unchecked")
    public static List<PaperBean> queryData(int voltage) {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        PaperBeanDao dao = daoSession.getPaperBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        List<PaperBean> list = (ArrayList<PaperBean>) qb.where(PaperBeanDao.Properties.VoltageLevel.eq(voltage)).list();
        if (list != null && !list.isEmpty()) {
            return list;
        }
        return Collections.emptyList();
    }



    @SuppressWarnings("unchecked")
    public static List<PaperBean> queryAllPaperData() {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        PaperBeanDao dao = daoSession.getPaperBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        List<PaperBean> list = (ArrayList<PaperBean>) qb.list();
        if (list != null && !list.isEmpty()) {
            return list;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static void deleteAll() {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        PaperBeanDao dao = daoSession.getPaperBeanDao();
        if(dao!=null){
            dao.deleteAll();
        }

    }
    @SuppressWarnings("unchecked")
    public static int getDataCount() {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        PaperBeanDao dao = daoSession.getPaperBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        List<PaperBean> list = (ArrayList<PaperBean>) qb.list();
        if (list != null && !list.isEmpty()) {
            return list.size();
        }
        return 0;

    }

    @SuppressWarnings("unchecked")
    public static void updateBean(PaperBean bean) {
        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        PaperBeanDao dao = daoSession.getPaperBeanDao();
        dao.update(bean);
    }


}
