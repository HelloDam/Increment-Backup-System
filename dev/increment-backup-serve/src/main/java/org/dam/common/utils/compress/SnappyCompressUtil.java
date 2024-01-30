package org.dam.common.utils.compress;

import org.dam.common.utils.FileUtils;
import org.xerial.snappy.Snappy;

import java.io.File;
import java.io.IOException;

/**
 * @Author dam
 * @create 2024/1/28 18:00
 */
public class SnappyCompressUtil {

    public static boolean compressFile(File sourceFile, String targetPath) {
        try {
            byte[] fileBytes = FileUtils.getFileBytes(sourceFile);
            byte[] compressedBytes = compress(fileBytes);
            FileUtils.outputFileBytes(compressedBytes, targetPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static byte[] compress(byte srcBytes[]) throws IOException {
        return  Snappy.compress(srcBytes);
    }

    public static byte[] uncompress(byte[] bytes) throws IOException {
        return Snappy.uncompress(bytes);
    }

}
