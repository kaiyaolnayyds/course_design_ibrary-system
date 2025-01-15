package com.example.booktracker.utils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteUtils  extends SQLiteOpenHelper {

    public SqliteUtils() {
        super(AppUtils.getApplication(), "book_tracker.db", null, 1);
    }

    /**
     * 创建并获取单例
     */
    public static SqliteUtils getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
        用户表(_user)：
        _id       integer  用户id
        username  varchar  用户名
        password  varchar  密码
         */
        sqLiteDatabase.execSQL("CREATE TABLE _user(_id INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(20) ,password VARCHAR(20))");
        /*
        图书表(_book):
        _id           integer  图书id
        name          varchar  书名
        author        varchar  作者
        description   varchar  描述
        url           varchar  图片url
        total         integer  总数量
        remain        integer  剩余数量
         */
        sqLiteDatabase.execSQL("CREATE TABLE _book(_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),author VARCHAR(20),description VARCHAR(200),url VARCHAR(200),total INTEGER,remain INTEGER)");
        /*
        借阅记录表(_borrow)：
        _id          integer   借阅id
        user_id      integer   用户id
        book_id      integer   图书id
        borrow_date  varchar   借出日期
        return_date  varchar   归还日期
         */
        sqLiteDatabase.execSQL("CREATE TABLE _borrow(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,book_id INTEGER,borrow_date VARCHAR(20),return_date VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    private static final class InstanceHolder {
        /**
         * 单例
         */
        static final SqliteUtils instance = new SqliteUtils();
    }
}
