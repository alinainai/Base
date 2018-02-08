package trunk.doi.base.gen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import trunk.doi.base.bean.CollectionBean;
import trunk.doi.base.bean.GankItemData;

/**
 * USer  Administrator ljx
 * Date  2018/2/7
 * Email 569932357@qq.com
 * Description :
 */

public class DatabaseService {

    private TrunkSQLiteOpenHelper dbOpenHelper;
    private static final String DATABASE_NAME= "collection";

    public DatabaseService(Context context) {
        dbOpenHelper = new TrunkSQLiteOpenHelper(context);
    }

    public void dropTable(String taleName) {
        dbOpenHelper.getWritableDatabase().execSQL(
                "DROP TABLE IF EXISTS " + taleName);
    }

    public void closeDatabase() {
        dbOpenHelper.getWritableDatabase().close();
    }

    /**
     * 参数一是表名称，
     * 参数二是空列的默认值，
     * 参数三是ContentValues类型的一个封装了列名称和列值的Map
     *
     * @param data
     * sql: execSQL("insert into GankItem ('desc',url,publishedAt,who) values ( ?, ?, ?, ?), new Object[])
     */
    public void addInfo(CollectionBean data) {
        //实例化常量值
        ContentValues cValue = new ContentValues();
        cValue.put("desc", data.getDesc());
        cValue.put("url", data.getUrl());
        cValue.put("dataTime", data.getDataTime());
        //调用insert()方法插入数据
        dbOpenHelper.getWritableDatabase().insert(DATABASE_NAME, null, cValue);
    }

    /**
     * add GankItemDatas
     * @param datas
     */
    public void addList(List<CollectionBean> datas) {
        dbOpenHelper.getWritableDatabase().beginTransaction();  //开始事务
        try {
            for (CollectionBean data : datas) {
                addInfo(data);
            }
            dbOpenHelper.getWritableDatabase().setTransactionSuccessful();  //设置事务成功完成
        } finally {
            dbOpenHelper.getWritableDatabase().endTransaction();    //结束事务
        }
    }

    /**
     *  根据id删除
     *  sql: execSQL("delete from collection where _id = ? ", new Object[]{})
     * @param gid
     */
    public void deleteGankInfo(String gid) {
        //删除条件
        String whereClause = "_id=?";
        //删除条件参数
        String[] whereArgs = {gid};
        //执行删除
        dbOpenHelper.getWritableDatabase().delete(DATABASE_NAME, whereClause, whereArgs);
    }

    /**
     * @param ids
     */
    public void deleteList(List<String> ids) {
        dbOpenHelper.getWritableDatabase().beginTransaction();  //开始事务
        try {
            for (String id : ids) {
                deleteGankInfo(id);
            }
            dbOpenHelper.getWritableDatabase().setTransactionSuccessful();  //设置事务成功完成
        } finally {
            dbOpenHelper.getWritableDatabase().endTransaction();    //结束事务
        }
    }

    /**
     * execSQL("update collection set 'desc' = ? , url = ? , dataTime = ? where _id = ?", new Object[]{})
     * @param data
     * @param gid
     */
    public void updateGank(CollectionBean data, String gid) {
        //实例化内容值
        ContentValues values = new ContentValues();
        values.put("desc", data.getDesc());
        values.put("url", data.getUrl());
        values.put("dataTime", data.getDataTime());
        //修改条件
        String whereClause = "_id=?";
        //修改添加参数
        String[] whereArgs = {gid};
        //修改
        dbOpenHelper.getWritableDatabase().update(DATABASE_NAME, values, whereClause, whereArgs);
    }


    /**
     * 查询全部信息
     * @return
     */
    public List<CollectionBean> queryPage(int page,int pageCount) {
        List<CollectionBean> datas=new ArrayList<>();
        //查询获得游标
        Cursor cursor = dbOpenHelper.getWritableDatabase().query(DATABASE_NAME,
                null, null, null, null,
                null, "_id desc",String.valueOf((page-1)*pageCount)+","+String.valueOf(pageCount));
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            CollectionBean data;
            do {
                data = new CollectionBean();
                data.set_id(String.valueOf(cursor.getInt(0)));
                data.setDesc(cursor.getString(1));
                data.setUrl(cursor.getString(2));
                data.setDataTime(cursor.getString(3));
                datas.add(data);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return datas;
    }

    /**
     * 查询全部信息
     * @return
     */
    public List<CollectionBean> queryAll() {
        List<CollectionBean> datas=new ArrayList<>();
        //查询获得游标
        Cursor cursor = dbOpenHelper.getWritableDatabase().query(DATABASE_NAME, null, null, null, null, null, "_id desc");
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            CollectionBean data;
            do {
                data = new CollectionBean();
                data.set_id(String.valueOf(cursor.getInt(0)));
                data.setDesc(cursor.getString(1));
                data.setUrl(cursor.getString(2));
                data.setDataTime(cursor.getString(3));
                datas.add(data);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return datas;
    }

    /**
     * 根据网址查询是否存在
     * @param url
     * @return
     */
    public CollectionBean query(String url) {
        String sql="select _id,'desc',url,dataTime from collection where url = ? ";
        CollectionBean data=null;
        Cursor cursor = dbOpenHelper
              .getReadableDatabase()
              .rawQuery(sql, new String[] { url});
       //判断游标是否为空
        if (cursor.moveToFirst()) {
                data = new CollectionBean();
                data.set_id(String.valueOf(cursor.getInt(0)));
                data.setDesc(cursor.getString(1));
                data.setUrl(cursor.getString(2));
                data.setDataTime(cursor.getString(3));
        }
        cursor.close();
        return data;
    }

}
