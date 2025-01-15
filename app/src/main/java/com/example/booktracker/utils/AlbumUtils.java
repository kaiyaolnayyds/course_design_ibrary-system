package com.example.booktracker.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class AlbumUtils {

    public final static int OPEN_ALBUM_REQUEST_CODE = 0xA1; //打开相册的请求码

    /**
     * 打开系统相册
     */
    public static void openAlbum(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, OPEN_ALBUM_REQUEST_CODE);
    }

    /**
     * 从Intent中获取图片路径
     */
    public static String getImagePath(Intent data) {
        if (data == null) {
            return null;
        }

        Uri uri = data.getData();
        if (uri == null) {
            return null;
        }

        Context context = AppUtils.getApplication();

        String imagePath = null;
        // 判断不同的Uri类型并处理
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的Uri, 通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePathFromUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePathFromUri(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri
            imagePath = getImagePathFromUri(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是文件类型的Uri，直接获取图片路径
            imagePath = uri.getPath();
        }

        return imagePath;
    }

    @SuppressLint("Range")
    private static String getImagePathFromUri(Uri uri, String selection) {
        Context context = AppUtils.getApplication();

        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
