@file:Suppress("UNCHECKED_CAST")

package com.gas.zhihu.utils

import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.db.DbUtils
import com.gas.zhihu.db.MapBeanDao
import com.lib.commonsdk.utils.AppUtils
import org.greenrobot.greendao.query.QueryBuilder
import java.util.*

object MapBeanDbUtils {
    @JvmStatic
    fun insertMapBean(bean: MapBean): Boolean {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.mapBeanDao
        val qb: QueryBuilder<*> = dao.queryBuilder()
        val list = qb.where(MapBeanDao.Properties.KeyName.eq(bean.keyName)).list()
        return if (list.size > 0) {
            false
        } else {
            dao.insert(bean)
            true
        }
    }

    fun delete(keyName: String?) {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.mapBeanDao
        val qb: QueryBuilder<MapBean> = dao.queryBuilder()
        val list = qb.where(MapBeanDao.Properties.KeyName.eq(keyName)).list()
        if (list.isNotEmpty()) {
            dao.delete(list[0])
        }
    }

    @JvmStatic
    fun queryData(keyName: String?): MapBean? {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.mapBeanDao
        val qb: QueryBuilder<MapBean> = dao.queryBuilder()
        val list = qb.where(MapBeanDao.Properties.KeyName.eq(keyName)).list() as ArrayList<MapBean>
        return if (list.isNotEmpty()) {
            list[0]
        } else null
    }

    fun queryAllMapData(): List<MapBean>{
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.mapBeanDao
        val qb: QueryBuilder<MapBean> = dao.queryBuilder()
        val list = qb.list()
        return if (list.isNotEmpty()) {
            list
        } else emptyList()
    }

    fun addComment(mapKey: String?, comment: String?): Boolean {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.mapBeanDao
        val qb: QueryBuilder<MapBean> = dao.queryBuilder()

        val list = qb.where(MapBeanDao.Properties.KeyName.eq(mapKey)).list()
        if (list.isNotEmpty()) {
            list[0].note=comment
            dao.update(list[0])
            return true
        }
        return false
    }

    @JvmStatic
    fun deleteAll() {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.mapBeanDao
        dao?.deleteAll()
    }

    @JvmStatic
    val mapDataCount: Int
        get() {
            val daoSession = DbUtils.getInstance().daoSession
            val dao = daoSession.mapBeanDao
            val qb: QueryBuilder<MapBean> = dao.queryBuilder()
            val list = qb.list()
            return if (list.isNotEmpty()) {
                list.size
            } else 0
        }

    @JvmStatic
    fun updateBean(bean: MapBean) {
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.mapBeanDao
        dao.update(bean)
    }

    @JvmStatic
    fun getSearchKeysLike(key: String): List<String> {
        val strs: MutableList<String> = ArrayList()
        val daoSession = DbUtils.getInstance().daoSession
        val dao = daoSession.mapBeanDao
        val qb: QueryBuilder<MapBean> = dao.queryBuilder()
        val list: List<MapBean> = qb.where(MapBeanDao.Properties.KeyName.like("%$key%")).list()
        if (list.isNotEmpty()) {
            for (mapBean in list) {
                strs.add(mapBean.keyName)
            }
        }
        return strs
    }
}