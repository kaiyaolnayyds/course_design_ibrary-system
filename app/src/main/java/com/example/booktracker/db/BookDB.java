package com.example.booktracker.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.booktracker.entity.Book;
import com.example.booktracker.utils.SqliteUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Range")
public class BookDB {

    /**
     * 添加图书
     */
    public static BusinessResult<Book> addBook(Book book) {
        BusinessResult<Book> result = new BusinessResult<>();
        if (book == null) {
            result.setSuccess(false);
            result.setMessage("图书信息不能为空");
            return result;
        }
        if(TextUtils.isEmpty(book.getName())){
            result.setSuccess(false);
            result.setMessage("书名不能为空");
            return result;
        }
        if(TextUtils.isEmpty(book.getAuthor())){
            result.setSuccess(false);
            result.setMessage("作者不能为空");
            return result;
        }
        if(TextUtils.isEmpty(book.getDesc())){
            result.setSuccess(false);
            result.setMessage("描述不能为空");
            return result;
        }
        if(TextUtils.isEmpty(book.getUrl())){
            result.setSuccess(false);
            result.setMessage("图片不能为空");
            return result;
        }
        if(book.getTotal() == null||book.getTotal()<=0){
            result.setSuccess(false);
            result.setMessage("总数量不能为空，且必须大于0");
            return result;
        }
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", book.getName());
        values.put("author", book.getAuthor());
        values.put("description", book.getDesc());
        values.put("url", book.getUrl());
        values.put("total", book.getTotal());
        values.put("remain", book.getTotal());
        long i = db.insert("_book", null, values);
        if (i > 0) {
            result.setSuccess(true);
            result.setMessage("添加成功");
            book.setId((int) i);
            result.setData(book);
        } else {
            result.setSuccess(false);
            result.setMessage("添加失败");
        }
        return result;
    }

    /**
     * 根据id获取图书
     */
    public static BusinessResult<Book> getBookById(int id) {
        BusinessResult<Book> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        Book book = null;
        Cursor cursor = db.query("_book", null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToNext()) {
            book = new Book();
            book.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            book.setName(cursor.getString(cursor.getColumnIndex("name")));
            book.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            book.setDesc(cursor.getString(cursor.getColumnIndex("description")));
            book.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            book.setTotal(cursor.getInt(cursor.getColumnIndex("total")));
            book.setRemain(cursor.getInt(cursor.getColumnIndex("remain")));
        }
        cursor.close();
        if (book != null) {
            result.setSuccess(true);
            result.setMessage("查询成功");
            result.setData(book);
        } else {
            result.setSuccess(false);
            result.setMessage("图书不存在");
        }
        return result;
    }


    /**
     * 编辑图书
     */
    public static BusinessResult<Book> updateBook(Book book) {
        BusinessResult<Book> result = new BusinessResult<>();
        if (book == null) {
            result.setSuccess(false);
            result.setMessage("图书信息不能为空");
            return result;
        }
        if(TextUtils.isEmpty(book.getName())){
            result.setSuccess(false);
            result.setMessage("书名不能为空");
            return result;
        }
        if(TextUtils.isEmpty(book.getAuthor())){
            result.setSuccess(false);
            result.setMessage("作者不能为空");
            return result;
        }
        if(TextUtils.isEmpty(book.getDesc())){
            result.setSuccess(false);
            result.setMessage("描述不能为空");
            return result;
        }
        if(TextUtils.isEmpty(book.getUrl())){
            result.setSuccess(false);
            result.setMessage("图片不能为空");
            return result;
        }
        if(book.getTotal() == null||book.getTotal()<=0){
            result.setSuccess(false);
            result.setMessage("总数量不能为空，且必须大于0");
            return result;
        }

        BusinessResult<Book> bookByIdResult = getBookById(book.getId());
        if (!bookByIdResult.isSuccess()) {
            result.setSuccess(false);
            result.setMessage(bookByIdResult.getMessage());
            return result;
        }
        Book bookById = bookByIdResult.getData();
        book.setRemain(bookById.getRemain()-bookById.getTotal()+book.getTotal());
        if (book.getRemain() < 0) {
            book.setRemain(0);
        }
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", book.getName());
        values.put("author", book.getAuthor());
        values.put("description", book.getDesc());
        values.put("url", book.getUrl());
        values.put("total", book.getTotal());
        values.put("remain", book.getRemain());
        int i = db.update("_book", values, "_id=?", new String[]{String.valueOf(book.getId())});
        if (i > 0) {
            result.setSuccess(true);
            result.setMessage("编辑成功");
            result.setData(book);
        } else {
            result.setSuccess(false);
            result.setMessage("编辑失败");
        }
        return result;
    }

    /**
     * 借阅图书
     */
    public static BusinessResult<Void> borrowBook(int bookId) {
        BusinessResult<Void> result = new BusinessResult<>();
        BusinessResult<Book> bookByIdResult = getBookById(bookId);
        if (!bookByIdResult.isSuccess()) {
            result.setSuccess(false);
            result.setMessage(bookByIdResult.getMessage());
            return result;
        }
        Book book = bookByIdResult.getData();
        if (book.getRemain() <= 0) {
            result.setSuccess(false);
            result.setMessage("图书已借完");
            return result;
        }
        book.setRemain(book.getRemain() - 1);
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("remain", book.getRemain());
        int i = db.update("_book", values, "_id=?", new String[]{String.valueOf(bookId)});
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
     * 归还图书
     */
    public static BusinessResult<Void> returnBook(int bookId) {
        BusinessResult<Void> result = new BusinessResult<>();
        BusinessResult<Book> bookByIdResult = getBookById(bookId);
        if (!bookByIdResult.isSuccess()) {
            result.setSuccess(false);
            result.setMessage(bookByIdResult.getMessage());
            return result;
        }
        Book book = bookByIdResult.getData();
        book.setRemain(book.getRemain() + 1);
        if (book.getRemain() > book.getTotal()) {
            book.setTotal(book.getRemain());
        }
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("remain", book.getRemain());
        values.put("total", book.getTotal());
        int i = db.update("_book", values, "_id=?", new String[]{String.valueOf(bookId)});
        if (i > 0) {
            result.setSuccess(true);
            result.setMessage("归还成功");
        } else {
            result.setSuccess(false);
            result.setMessage("归还失败");
        }
        return result;
    }

    /**
     * 删除图书
     */
    public static BusinessResult<Void> deleteBook(int bookId) {
        BusinessResult<Void> result = new BusinessResult<>();
        BusinessResult<Book> bookByIdResult = getBookById(bookId);
        if (!bookByIdResult.isSuccess()) {
            result.setSuccess(false);
            result.setMessage(bookByIdResult.getMessage());
            return result;
        }
        SQLiteDatabase db = SqliteUtils.getInstance().getWritableDatabase();
        int i = db.delete("_book", "_id=?", new String[]{String.valueOf(bookId)});
        if (i > 0) {
            result.setSuccess(true);
            result.setMessage("删除成功");
        } else {
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 获取所有图书
     */
    public static BusinessResult<List<Book>> getAllBooks() {
        BusinessResult<List<Book>> result = new BusinessResult<>();
        SQLiteDatabase db = SqliteUtils.getInstance().getReadableDatabase();
        List<Book> books = new ArrayList<>();
        //根据id倒序查询
        Cursor cursor = db.rawQuery("select * from _book order by _id desc", null);
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            book.setName(cursor.getString(cursor.getColumnIndex("name")));
            book.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            book.setDesc(cursor.getString(cursor.getColumnIndex("description")));
            book.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            book.setTotal(cursor.getInt(cursor.getColumnIndex("total")));
            book.setRemain(cursor.getInt(cursor.getColumnIndex("remain")));
            books.add(book);
        }
        cursor.close();
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(books);
        return result;
    }
}
