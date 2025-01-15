package com.example.booktracker.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.booktracker.entity.User;
import com.example.booktracker.utils.MD5Utils;
import com.example.booktracker.utils.SqliteUtils;

public class UserDB {

    /**
     * 注册用户
     */
    public static BusinessResult<User> register(User user, String confirmPassword) {
        BusinessResult<User> result = new BusinessResult<>();
        if (user == null) {
            result.setSuccess(false);
            result.setMessage("用户信息不能为空");
            return result;
        }
        if (isExistByUsername(user.getUsername()).getData()) {
            result.setSuccess(false);
            result.setMessage("用户已存在");
            return result;
        }
        if (TextUtils.isEmpty(user.getUsername()) || TextUtils.isEmpty(user.getPassword())) {
            result.setSuccess(false);
            result.setMessage("用户名或密码不能为空");
            return result;
        }
        if (!TextUtils.equals(user.getPassword(), confirmPassword)) {
            result.setSuccess(false);
            result.setMessage("两次密码不一致");
            return result;
        }
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", MD5Utils.md5(user.getPassword()));
        long i = db.insert("_user", null, values);
        if (i > 0) {
            result.setSuccess(true);
            result.setMessage("注册成功");
            user.setId((int) i);
            result.setData(user);
        } else {
            result.setSuccess(false);
            result.setMessage("注册失败");
        }
        return result;
    }

    /**
     * 登录用户
     */
    @SuppressLint("Range")
    public static BusinessResult<User> login(User user) {
        BusinessResult<User> result = new BusinessResult<>();
        if (user == null) {
            result.setSuccess(false);
            result.setMessage("用户信息不能为空");
            return result;
        }
        if (TextUtils.isEmpty(user.getUsername()) || TextUtils.isEmpty(user.getPassword())) {
            result.setSuccess(false);
            result.setMessage("用户名或密码不能为空");
            return result;
        }
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        Cursor cursor = db.query("_user", null, "username=? and password=?", new String[]{user.getUsername(), MD5Utils.md5(user.getPassword())}, null, null, null);
        if (cursor.moveToNext()) {
            result.setSuccess(true);
            result.setMessage("登录成功");
            user.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            result.setData(user);
        } else {
            result.setSuccess(false);
            result.setMessage("用户名或密码错误");
        }
        cursor.close();
        return result;
    }

    /**
     * 根据用户名查询是否存在该用户
     */
    public static BusinessResult<Boolean> isExistByUsername(String username) {
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        Cursor cursor = db.query("_user", null, "username=?", new String[]{username}, null, null, null);
        BusinessResult<Boolean> result = new BusinessResult<>();
        result.setSuccess(true);
        if (cursor.getCount() > 0) {
            result.setData(true);
            result.setMessage("用户已存在");
        } else {
            result.setData(false);
            result.setMessage("用户不存在");
        }
        cursor.close();
        return result;
    }

}
