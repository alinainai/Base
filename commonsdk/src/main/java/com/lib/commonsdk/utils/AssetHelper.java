package com.lib.commonsdk.utils;


import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ================================================
 * desc:
 * <p>
 * created by author ljx
 * date  2020/4/12
 * email 569932357@qq.com
 * <p>
 * ================================================
 */
public class AssetHelper {
    private static final String SEPARATOR = File.separator;//路径分隔符


    /**
     * 复制assets中的文件到指定目录
     *
     * @param context    上下文
     * @param assetsPath assets资源路径*
     */
    public static void getTextFromAssetsFile(Context context, String assetsPath) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            //获取assets资源管理器
            AssetManager assetManager = context.getApplicationContext().getAssets();
            //通过管理器打开文件并读取
            InputStreamReader reader = new InputStreamReader(assetManager.open(assetsPath));
            BufferedReader bf = new BufferedReader(reader);
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 复制assets中的文件到指定目录
     *
     * @param context     上下文
     * @param assetsPath  assets资源路径
     * @param storagePath 目标文件夹的路径
     */
    public static void copyFilesFromAssets(Context context, String assetsPath, String storagePath) {
        String temp = "";

        if (TextUtils.isEmpty(storagePath)) {
            return;
        } else if (storagePath.endsWith(SEPARATOR)) {
            storagePath = storagePath.substring(0, storagePath.length() - 1);
        }

        if (TextUtils.isEmpty(assetsPath) || assetsPath.equals(SEPARATOR)) {
            assetsPath = "";
        } else if (assetsPath.endsWith(SEPARATOR)) {
            assetsPath = assetsPath.substring(0, assetsPath.length() - 1);
        }

        AssetManager assetManager = context.getApplicationContext().getAssets();
        try {
            File file = new File(storagePath);
            if (!file.exists()) {//如果文件夹不存在，则创建新的文件夹
                file.mkdirs();
            }

            // 获取assets目录下的所有文件及目录名
            String[] fileNames = assetManager.list(assetsPath);
            if (fileNames != null) {
                if (fileNames.length > 0) {//如果是目录 apk
                    for (String fileName : fileNames) {
                        if (!TextUtils.isEmpty(assetsPath)) {
                            temp = assetsPath + SEPARATOR + fileName;//补全assets资源路径
                        }

                        String[] childFileNames = assetManager.list(temp);
                        if (!TextUtils.isEmpty(temp) && childFileNames.length > 0) {//判断是文件还是文件夹：如果是文件夹
                            copyFilesFromAssets(context, temp, storagePath + SEPARATOR + fileName);
                        } else {//如果是文件
                            InputStream inputStream = assetManager.open(temp);
                            readInputStream(storagePath + SEPARATOR + fileName, inputStream);
                        }
                    }
                } else {//如果是文件 doc_test.txt或者apk/app_test.apk
                    InputStream inputStream = assetManager.open(assetsPath);
                    if (assetsPath.contains(SEPARATOR)) {//apk/app_test.apk
                        assetsPath = assetsPath.substring(assetsPath.lastIndexOf(SEPARATOR), assetsPath.length());
                    }
                    readInputStream(storagePath + SEPARATOR + assetsPath, inputStream);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取输入流中的数据写入输出流
     *
     * @param storagePath 目标文件路径
     * @param inputStream 输入流
     */
    public static void readInputStream(String storagePath, InputStream inputStream) {
        File file = new File(storagePath);
        try {
            if (!file.exists()) {
                // 1.建立通道对象
                FileOutputStream fos = new FileOutputStream(file);
                // 2.定义存储空间
                byte[] buffer = new byte[inputStream.available()];
                // 3.开始读文件
                int lenght = 0;
                while ((lenght = inputStream.read(buffer)) != -1) {// 循环从输入流读取buffer字节
                    // 将Buffer中的数据写到outputStream对象中
                    fos.write(buffer, 0, lenght);
                }
                fos.flush();// 刷新缓冲区
                // 4.关闭流
                fos.close();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
