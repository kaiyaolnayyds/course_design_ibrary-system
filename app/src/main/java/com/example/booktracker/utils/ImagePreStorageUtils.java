package com.example.booktracker.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.DrawableRes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImagePreStorageUtils {

    /**
     * 将drawable的图片存储到系统相册
     */
    public static String saveDrawableImage(@DrawableRes int drawableResId, String fileName) {
        Bitmap bitmap = drawableToBitmap(drawableResId);
        return saveBitmapImage(bitmap, fileName);
    }

    /**
     * 将bitmap图片存储到到系统相册
     */
    public static String saveBitmapImage(Bitmap bitmap, String fileName) {

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!folder.exists()) {
            boolean mkdir = folder.mkdir();
            if (!mkdir) {
                Log.e("ImagePreStorageUtils", "failed_to_create_directory");
                throw new RuntimeException("failed_to_create_directory");
            }
        }
        Context context = AppUtils.getApplication();
        //判断文件是否存在，存在则跳过
        File file = new File(folder, fileName+".png");
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        //用线程保存图片，避免阻塞主线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //通知系统相册更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            }
        }).start();
        return file.getAbsolutePath();
    }

    /**
     * 将drawable转换为bitmap
     */
    private static Bitmap drawableToBitmap(@DrawableRes int drawableResId) {
        Context context = AppUtils.getApplication();
        return BitmapFactory.decodeResource(context.getResources(), drawableResId);
    }

}
