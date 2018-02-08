package trunk.doi.base.gen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * USer  Administrator ljx
 * Date  2018/2/6
 * Email 569932357@qq.com
 * Description :
 */

public class TrunkSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "trunk.db";//数据库名字
    private static final int DATABASE_VERSION = 2;//数据库版本号
    private static final String CREATE_TABLE = "create table GankItem ("
            + "gid integer primary key autoincrement,"
            + "desc text, "
            + "url text, "
            + "publishedAt text, "
            + "who text)";//数据库里的表
    private static final String CREATE_TABLE_COLLECTION = "create table collection ("
            + "_id integer primary key autoincrement,"
            + "desc text, "
            + "url text, "
            + "dataTime text)";//数据库里的表

    public TrunkSQLiteOpenHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private TrunkSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);//调用到SQLiteOpenHelper中
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion==2){
            db.execSQL(CREATE_TABLE_COLLECTION);
            db.execSQL("DROP TABLE IF EXISTS GankItem");
        }


    }
}
