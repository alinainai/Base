package com.gas.zhihu.utils;

import com.gas.zhihu.bean.ExperienceBean;
import com.gas.zhihu.bean.MapBean;
import com.gas.zhihu.db.DaoSession;
import com.gas.zhihu.db.DbUtils;
import com.gas.zhihu.db.ExperienceBeanDao;
import com.gas.zhihu.db.MapBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExperienceBeanDbUtils {


    public static void insertMapBean(ExperienceBean bean) {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        ExperienceBeanDao dao = daoSession.getExperienceBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        List list = qb.where(ExperienceBeanDao.Properties.PathName.eq(bean.getPathName()),
                ExperienceBeanDao.Properties.VoltageLevel.eq(bean.getVoltageLevel()),
                ExperienceBeanDao.Properties.MapKey.eq(bean.getMapKey()),
                ExperienceBeanDao.Properties.FileName.eq(bean.getFileName())).list();
        if (list.size() > 0) {
        } else {
            dao.insert(bean);
        }

    }

    @SuppressWarnings("unchecked")
    public static void delete(String path) {
        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        ExperienceBeanDao dao = daoSession.getExperienceBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        List<ExperienceBean> list = (ArrayList<ExperienceBean>) qb.where(ExperienceBeanDao.Properties.PathName.eq(path)).list();
        if (list != null && !list.isEmpty()) {
            dao.delete(list.get(0));
        }
    }


    @SuppressWarnings("unchecked")
    public static List<ExperienceBean> queryData(int voltage) {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        ExperienceBeanDao dao = daoSession.getExperienceBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        List<ExperienceBean> list = (ArrayList<ExperienceBean>) qb.where(ExperienceBeanDao.Properties.VoltageLevel.eq(voltage)).list();
        if (list != null && !list.isEmpty()) {
            return list;
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    public static List<ExperienceBean> queryAllExperienceData() {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        ExperienceBeanDao dao = daoSession.getExperienceBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        List<ExperienceBean> list = (ArrayList<ExperienceBean>) qb.list();
        if (list != null && !list.isEmpty()) {
            return list;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static void deleteAll() {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        ExperienceBeanDao dao = daoSession.getExperienceBeanDao();
        if(dao!=null){
            dao.deleteAll();
        }

    }
    @SuppressWarnings("unchecked")
    public static int getDataCount() {

        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        ExperienceBeanDao dao = daoSession.getExperienceBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        List<ExperienceBean> list = (ArrayList<ExperienceBean>) qb.list();
        if (list != null && !list.isEmpty()) {
            return list.size();
        }
        return 0;

    }

    @SuppressWarnings("unchecked")
    public static void updateBean(ExperienceBean bean) {
        DaoSession daoSession = DbUtils.getInstance().getDaoSession();
        ExperienceBeanDao dao = daoSession.getExperienceBeanDao();
        dao.update(bean);
    }



}
