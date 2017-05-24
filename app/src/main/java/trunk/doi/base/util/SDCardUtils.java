package trunk.doi.base.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by ly on 2016/5/26 11:16.
 * SDCard相关的辅助类
 */
public class SDCardUtils {

    private SDCardUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 是否存在SDCard(1)
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SDCard根路径(2)
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte(3)
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte(4)
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径(5)
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 创建主目录(/ajy)
     */
    public static String getResourcePath() {
        String path = getSDCardPath() + "ajy" + File.separator;
        if (filesExists(getSDCardPath())) {
            if (!filesExists(path)) {
                File file = new File(path);
                file.mkdirs();
            }
        }
        return path;
    }

    private static boolean filesExists(String sdPath) {
        return new File(sdPath).exists();
    }

    /**
     * 根据文件名创建目录(/ajy/path/)
     */
    public static String getPath(String path) {
        String filePath = getResourcePath();
        String datePath = null;
        if (filesExists(filePath)) {
            datePath = filePath + path + File.separator;
            File file = new File(datePath);
            file.mkdirs();
        }
        return datePath;
    }

    /**
     * 場景路径
     */
    public static String marScenePath = getResourcePath() + "data/scene" + File.separator;


    public static String getScenePath(String sceneType) {
        String sceneDir = marScenePath + sceneType + File.separator;
        return sceneDir;
    }

    /**
     * 获取产品库或者数据库文件的路径
     */
    public static String getProductPath(String ProductType, String brandCode) {
        String path = "data" + File.separator + ProductType + File.separator + brandCode;
        return path;
    }

    /**
     * 硬盘缓存地址
     */
    public static String marcpoloCachePath = getResourcePath() + "imageloader/Cache";

    /**
     * SDCARD exists
     */
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static boolean fileIsExists(String sdPath) {
        return new File(sdPath).exists();
    }

    //=======================================================================================================================
    /**
     * Check the SD card
     *
     * @return 是否存在SDCard
     */
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * Check if the file is exists
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return 是否存在文件
     */
    public static boolean isFileExistsInSDCard(String filePath, String fileName) {
        boolean flag = false;
        if (checkSDCardAvailable()) {
            File file = new File(filePath, fileName);
            if (file.exists()) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Write file to SD card
     *
     * @param filePath 文件路径
     * @param filename 文件名
     * @param content  内容
     * @return 是否保存成功
     * @throws Exception
     */
    public static boolean saveFileToSDCard(String filePath, String filename,
                                           String content) throws Exception {
        boolean flag = false;
        if (checkSDCardAvailable()) {
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(filePath, filename);
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(content.getBytes());
            outStream.close();
            flag = true;
        }
        return flag;
    }

    /**
     * Read file as stream from SD card
     *
     * @param fileName String PATH =
     *                 Environment.getExternalStorageDirectory().getAbsolutePath() +
     *                 "/dirName";
     * @return Byte数组
     */
    public static byte[] readFileFromSDCard(String filePath, String fileName) {
        byte[] buffer = null;
        try {
            if (checkSDCardAvailable()) {
                String filePaht = filePath + "/" + fileName;
                FileInputStream fin = new FileInputStream(filePaht);
                int length = fin.available();
                buffer = new byte[length];
                fin.read(buffer);
                fin.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * Delete file
     *
     * @param filePath 文件路径
     * @param fileName filePath =
     *                 android.os.Environment.getExternalStorageDirectory().getPath()
     * @return 是否删除成功
     */
    public static boolean deleteSDFile(String filePath, String fileName) {
        File file = new File(filePath + "/" + fileName);
        if (!file.exists() || file.isDirectory())
            return false;
        return file.delete();
    }
}
