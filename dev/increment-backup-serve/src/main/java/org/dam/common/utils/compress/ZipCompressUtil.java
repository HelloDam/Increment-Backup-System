package org.dam.common.utils.compress;

import java.io.IOException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import java.io.*;
import java.util.zip.ZipEntry;

/**
 * @Author dam
 * @create 2024/1/28 18:04
 */
public class ZipCompressUtil {

    /**
     * 压缩文件
     *
     * @param sourceFile    源文件
     * @param destFilePath  目标文件路径
     * @throws IOException
     */
    public static void compressToFile(File sourceFile, String destFilePath) throws IOException {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        try {
            // 创建输出流
            fos = new FileOutputStream(destFilePath);
            zos = new ZipOutputStream(fos);

            // 压缩文件
            compress(sourceFile, zos);

            // 关闭输出流
            zos.close();
            fos.close();
        } catch (Exception e) {
            if (zos != null) {
                zos.close();
            }
            if (fos != null) {
                fos.close();
            }
            throw e;
        }
    }

    /**
     * 解压缩文件
     *
     * @param zipFilePath  压缩文件路径
     * @param destFilePath 解压路径
     * @throws IOException
     */
    public static void uncompressFile(String zipFilePath, String destFilePath) throws IOException {
        FileInputStream fis = null;
        ZipInputStream zis = null;

        try {
            // 创建输入流
            fis = new FileInputStream(zipFilePath);
            zis = new ZipInputStream(fis);

            // 创建目录
            File destDir = new File(destFilePath);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            // 解压缩文件
            ZipEntry zipEntry;
            byte[] buffer = new byte[1024];
            while ((zipEntry = zis.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(destDir.getAbsolutePath() + "\\" + fileName);

                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                    continue;
                }

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(newFile);

                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                } catch (IOException e) {
                    throw e;
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
        } catch (Exception e) {
            if (zis != null) {
                zis.close();
            }
            if (fis != null) {
                fis.close();
            }
            throw e;
        }
    }

    /**
     * 压缩文件
     *
     * @param file    源文件
     * @param zos     zip输出流
     * @throws IOException
     */
    private static void compress(File file, ZipOutputStream zos) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                compress(f, zos);
            }
        } else {
            byte[] buffer = new byte[1024];
            BufferedInputStream bis = null;
            try {
                // 添加ZipEntry
                zos.putNextEntry(new ZipEntry(file.getName()));
                bis = new BufferedInputStream(new FileInputStream(file));

                // 写入文件流
                int read;
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                    zos.write(buffer, 0, read);
                }

                // 关闭ZipEntry
                zos.closeEntry();
            } catch (IOException e) {
                throw e;
            } finally {
                if (bis != null) {
                    bis.close();
                }
            }
        }
    }
}
