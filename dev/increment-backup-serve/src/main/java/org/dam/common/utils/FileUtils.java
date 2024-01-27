package org.dam.common.utils;

import java.io.File;

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
}
