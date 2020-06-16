package com.gas.zhihu.utils

import com.gas.zhihu.bean.PaperBean
import com.gas.zhihu.db.DbUtils
import com.gas.zhihu.db.PaperBeanDao
import org.greenrobot.greendao.query.QueryBuilder
import java.util.*

object PagerBeanDbUtils {
    @JvmStatic
    fun insertMapBean(bean: PaperBean): Boolean {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.paperBeanDao
        val qb = dao.queryBuilder()
        val list = qb.where(PaperBeanDao.Properties.PathName.eq(bean.pathName),
                PaperBeanDao.Properties.VoltageLevel.eq(bean.voltageLevel),
                PaperBeanDao.Properties.MapKey.eq(bean.mapKey),
                PaperBeanDao.Properties.FileName.eq(bean.fileName)).list()
        return if (list.size > 0) {
            false
        } else {
            dao.insert(bean)
            true
        }
    }

    @JvmStatic
    fun queryData(bean: PaperBean): PaperBean? {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.paperBeanDao
        val qb: QueryBuilder<PaperBean> = dao.queryBuilder()
        val list: List<PaperBean> = qb.where(PaperBeanDao.Properties.PathName.eq(bean.pathName),
                PaperBeanDao.Properties.VoltageLevel.eq(bean.voltageLevel),
                PaperBeanDao.Properties.MapKey.eq(bean.mapKey),
                PaperBeanDao.Properties.FileName.eq(bean.fileName)).list()
        return if (list.isNotEmpty()) {
            list[0]
        } else null

    }

    @JvmStatic
    fun delete(path: String?) {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.paperBeanDao
        val qb: QueryBuilder<PaperBean> = dao.queryBuilder()
        val list: List<PaperBean> = qb.where(PaperBeanDao.Properties.PathName.eq(path)).list() as ArrayList<PaperBean>
        if (list.isNotEmpty()) {
            dao.delete(list[0])
        }
    }

    @JvmStatic
    fun queryData(voltage: Int): List<PaperBean> {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.paperBeanDao
        val qb = dao.queryBuilder()
        val list: List<PaperBean> = qb.where(PaperBeanDao.Properties.VoltageLevel.eq(voltage)).list() as ArrayList<PaperBean>
        return if (list.isNotEmpty()) {
            list
        } else emptyList()
    }

    @JvmStatic
    fun queryAllPaperData(): List<PaperBean>? {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.paperBeanDao
        val qb: QueryBuilder<PaperBean> = dao.queryBuilder()
        val list: List<PaperBean> = qb.list()
        return if (list.isNotEmpty()) {
            list
        } else null
    }

    @JvmStatic
    fun queryAllPaperDataByType(type: Int): List<PaperBean> {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.paperBeanDao
        val qb: QueryBuilder<PaperBean> = dao.queryBuilder()
        val list: List<PaperBean> = qb.where(PaperBeanDao.Properties.Type.eq(type)).list()
        return if (list.isNotEmpty()) {
            list
        } else emptyList()
    }

    @JvmStatic
    fun deleteAll() {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.paperBeanDao
        dao?.deleteAll()
    }

    @JvmStatic
    val dataCount: Int
        get() {
            val daoSession = DbUtils.getInstance().daoSession
            val dao = daoSession.paperBeanDao
            val qb = dao.queryBuilder()
            val list: List<PaperBean> = qb.list() as ArrayList<PaperBean>
            return if (list.isNotEmpty()) {
                list.size
            } else 0
        }

    @JvmStatic
    fun updateBean(bean: PaperBean) {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.paperBeanDao
        dao.update(bean)
    }
}