package com.example.booktracker.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.booktracker.entity.User;

public class CurrentUserUtils {

    private static final String CURRENT_USER = "CURRENT_USER";

    private final SharedPreferences sp;

    public CurrentUserUtils() {
        sp = AppUtils.getApplication().getSharedPreferences(CURRENT_USER, Context.MODE_PRIVATE);
    }

    /**
     * 创建并获取单例
     */
    private static CurrentUserUtils getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * 获取当前用户
     */
    public static User getCurrentUser() {
        Integer id = getInstance().sp.getInt("id", 0);
        String username = getInstance().sp.getString("username", null);
        String password = getInstance().sp.getString("password", null);
        Boolean remember = getInstance().sp.getBoolean("remember", false);
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRemember(remember);
        return user;
    }

    /**
     * 设置当前用户
     */
    public static void setCurrentUser(User user) {
        SharedPreferences.Editor editor = getInstance().sp.edit();
        editor.putInt("id", user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.putBoolean("remember", user.getRemember() != null && user.getRemember());
        editor.apply();
    }

    private static final class InstanceHolder {
        /**
         * 单例
         */
        static final CurrentUserUtils instance = new CurrentUserUtils();
    }
}
