package com.example.booktracker.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.booktracker.entity.Book;
import com.example.booktracker.entity.Borrow;
import com.example.booktracker.utils.DateUtils;
import com.example.booktracker.utils.SqliteUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("Range")
public class BorrowDB {

    /**
     * 借阅
     */
    public static BusinessResult<Void> borrowBook(int userId, int bookId) {
        BusinessResult<Void> result = new BusinessResult<>();
        //更新图书数量
        BusinessResult<Void> borrowBookResult = BookDB.borrowBook(bookId);
        if (!borrowBookResult.isSuccess()) {
            result.setSuccess(false);
            result.setMessage(borrowBookResult.getMessage());
            return result;
        }
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("book_id", bookId);
        values.put("borrow_date", DateUtils.format(new Date()));
        long i = db.insert("_borrow", null, values);
        if (i > 0) {
            result.setSuccess(true);
            result.setMessage("借阅成功");
        } else {
            result.setSuccess(false);
            result.setMessage("借阅失败");
        }
        return result;
    }

    /**
     * 归还
     */
    public static BusinessResult<String> returnBook(int borrowId,int bookId) {
        BusinessResult<String> result = new BusinessResult<>();
        //更新图书数量
        BusinessResult<Void> returnBookResult = BookDB.returnBook(bookId);
        if (!returnBookResult.isSuccess()) {
            result.setSuccess(false);
            result.setMessage(returnBookResult.getMessage());
            return result;
        }

        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        //写入归还日期
        ContentValues values = new ContentValues();
        String returnDate = DateUtils.format(new Date());
        values.put("return_date", returnDate);
        int i = db.update("_borrow", values, "_id=?", new String[]{String.valueOf(borrowId)});
        if (i > 0) {
            result.setSuccess(true);
            result.setMessage("归还成功");
            result.setData(returnDate);
        } else {
            result.setSuccess(false);
            result.setMessage("归还失败");
        }
        return result;
    }

    /**
     * 查询借阅记录
     * type 1 借阅中 2 已归还
     */
    public static BusinessResult<List<Borrow>> queryBorrowList(int userId,String bookName,int type) {
        BusinessResult<List<Borrow>> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        Cursor cursor = null;
        //倒序查询
        if (type == 1) {
            cursor = db.rawQuery("select * from _borrow where user_id=? and return_date is null order by _id desc", new String[]{String.valueOf(userId)});
        } else if (type == 2) {
            cursor = db.rawQuery("select * from _borrow where user_id=? and return_date is not null order by _id desc", new String[]{String.valueOf(userId)});
        } else {
            cursor = db.rawQuery("select * from _borrow where user_id=? order by _id desc", new String[]{String.valueOf(userId)});
        }
        List<Borrow> borrowList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int bookId = cursor.getInt(cursor.getColumnIndex("book_id"));
            BusinessResult<Book> bookByIdResult = BookDB.getBookById(bookId);
            if (!bookByIdResult.isSuccess()) {
                continue;
            }
            Book book = bookByIdResult.getData();
            //根据书名筛选,如果书名不包含搜索的书名，则跳过,否则添加到列表
            if (!TextUtils.isEmpty(bookName) && !book.getName().contains(bookName)) {
                continue;
            }
            Borrow borrow = new Borrow();
            borrow.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            borrow.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
            borrow.setBookId(bookId);
            borrow.setBookName(book.getName());
            borrow.setBorrowDate(cursor.getString(cursor.getColumnIndex("borrow_date")));
            borrow.setReturnDate(cursor.getString(cursor.getColumnIndex("return_date")));
            borrowList.add(borrow);
        }
        cursor.close();
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(borrowList);
        return result;
    }
}
