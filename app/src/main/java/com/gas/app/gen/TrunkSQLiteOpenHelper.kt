package com.gas.app.gen

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

/**
 * USer  Administrator ljx
 * Date  2018/2/6
 * Email 569932357@qq.com
 * Description :
 */
class TrunkSQLiteOpenHelper private constructor(context: Context, name: String, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    constructor(context: Context) : this(context, DATABASE_NAME, null, DATABASE_VERSION) {}

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_COLLECTION)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

//        if(newVersion>=2){
//            db.execSQL(CREATE_TABLE_COLLECTION);
//            db.execSQL("DROP TABLE IF EXISTS GankItem");
//        }
    }

    companion object {
        private const val DATABASE_NAME = "trunk.db" //数据库名字
        private const val DATABASE_VERSION = 1 //数据库版本号
        private const val CREATE_TABLE_COLLECTION = ("create table collection ("
                + "_id integer primary key autoincrement,"
                + "_desc text, "
                + "url text, "
                + "dataTime text) ") //数据库里的表
    }
}