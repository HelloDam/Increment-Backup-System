package org.dam.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author dam
 * @create 2024/1/27 13:39
 */
public class FileUtils {
    /**
     * 递归删除文件夹下的所有文件
     * @param file
     * @return
     */
    public static boolean recursionDeleteFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File sonFile : files) {
                    recursionDeleteFiles(sonFile);
                }
            }
        }
        return file.delete();
    }

    /**
     * 将字节数组输出为文件
     * @param bytes
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean outputFileBytes(byte[] bytes, String path) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(path);
        try {
            outputStream.write(bytes);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            outputStream.close();
        }
    }

    /**
     * 获取文件的byte数组
     *
     * @param file 文件
     * @return 文件byte数组
     * @throws IOException
     */
    public static byte[] getFileBytes(File file) throws IOException {
        byte[] fileBytes = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileBytes);
        }
        return fileBytes;
    }

}
