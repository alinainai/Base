package com.gas.app.gen

import android.content.ContentValues
import android.content.Context
import com.gas.app.bean.CollectionBean
import java.util.*

/**
 * USer  Administrator ljx
 * Date  2018/2/7
 * Email 569932357@qq.com
 * Description :
 */
class DatabaseService(context: Context) {
    private val dbOpenHelper: TrunkSQLiteOpenHelper = TrunkSQLiteOpenHelper(context)
    fun dropTable(taleName: String) {
        dbOpenHelper.writableDatabase.execSQL(
                "DROP TABLE IF EXISTS $taleName")
    }

    fun closeDatabase() {
        dbOpenHelper.writableDatabase.close()
    }

    /**
     * 参数一是表名称，
     * 参数二是空列的默认值，
     * 参数三是ContentValues类型的一个封装了列名称和列值的Map
     *
     * @param data
     * sql: execSQL("insert into GankItem ('desc',url,publishedAt,who) values ( ?, ?, ?, ?), new Object[])
     */
    fun addInfo(data: CollectionBean) {
        //实例化常量值
        val cValue = ContentValues()
        cValue.put("desc", data.desc)
        cValue.put("url", data.url)
        cValue.put("dataTime", data.dataTime)
        //调用insert()方法插入数据
        dbOpenHelper.writableDatabase.insert(DATABASE_NAME, null, cValue)
    }

    /**
     * add GankItemDatas
     * @param datas
     */
    fun addList(datas: List<CollectionBean>) {
        dbOpenHelper.writableDatabase.beginTransaction() //开始事务
        try {
            for (data in datas) {
                addInfo(data)
            }
            dbOpenHelper.writableDatabase.setTransactionSuccessful() //设置事务成功完成
        } finally {
            dbOpenHelper.writableDatabase.endTransaction() //结束事务
        }
    }

    /**
     * 根据id删除
     * sql: execSQL("delete from collection where _id = ? ", new Object[]{})
     * @param gid
     */
    fun deleteGankInfo(gid: String) {
        //删除条件
        val whereClause = "_id=?"
        //删除条件参数
        val whereArgs = arrayOf(gid)
        //执行删除
        dbOpenHelper.writableDatabase.delete(DATABASE_NAME, whereClause, whereArgs)
    }

    /**
     * @param ids
     */
    fun deleteList(ids: List<String>) {
        dbOpenHelper.writableDatabase.beginTransaction() //开始事务
        try {
            for (id in ids) {
                deleteGankInfo(id)
            }
            dbOpenHelper.writableDatabase.setTransactionSuccessful() //设置事务成功完成
        } finally {
            dbOpenHelper.writableDatabase.endTransaction() //结束事务
        }
    }

    /**
     * execSQL("update collection set 'desc' = ? , url = ? , dataTime = ? where _id = ?", new Object[]{})
     * @param data
     * @param gid
     */
    fun updateGank(data: CollectionBean, gid: String) {
        //实例化内容值
        val values = ContentValues()
        values.put("desc", data.desc)
        values.put("url", data.url)
        values.put("dataTime", data.dataTime)
        //修改条件
        val whereClause = "_id=?"
        //修改添加参数
        val whereArgs = arrayOf(gid)
        //修改
        dbOpenHelper.writableDatabase.update(DATABASE_NAME, values, whereClause, whereArgs)
    }

    /**
     * 查询全部信息
     * @return
     */
    fun queryPage(page: Int, pageCount: Int): List<CollectionBean> {
        val datas: MutableList<CollectionBean> = ArrayList()
        //查询获得游标
        val cursor = dbOpenHelper.writableDatabase.query(DATABASE_NAME,
                null, null, null, null,
                null, "_id desc", ((page - 1) * pageCount).toString() + "," + pageCount.toString())
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            var data: CollectionBean
            do {
                data = CollectionBean()
                data.set_id(cursor.getInt(0).toString())
                data.desc = cursor.getString(1)
                data.url = cursor.getString(2)
                data.dataTime = cursor.getString(3)
                datas.add(data)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return datas
    }

    /**
     * 查询全部信息
     * @return
     */
    fun queryAll(): List<CollectionBean> {
        val datas: MutableList<CollectionBean> = ArrayList()
        //查询获得游标
        val cursor = dbOpenHelper.writableDatabase.query(DATABASE_NAME, null, null, null, null, null, "_id desc")
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            var data: CollectionBean
            do {
                data = CollectionBean()
                data.set_id(cursor.getInt(0).toString())
                data.desc = cursor.getString(1)
                data.url = cursor.getString(2)
                data.dataTime = cursor.getString(3)
                datas.add(data)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return datas
    }

    /**
     * 根据网址查询是否存在
     * @param url
     * @return
     */
    fun query(url: String): CollectionBean? {
        val sql = "select _id,'desc',url,dataTime from collection where url = ? "
        var data: CollectionBean? = null
        val cursor = dbOpenHelper
                .readableDatabase
                .rawQuery(sql, arrayOf(url))
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            data = CollectionBean()
            data.set_id(cursor.getInt(0).toString())
            data.desc = cursor.getString(1)
            data.url = cursor.getString(2)
            data.dataTime = cursor.getString(3)
        }
        cursor.close()
        return data
    }

    companion object {
        private const val DATABASE_NAME = "collection"
    }

}