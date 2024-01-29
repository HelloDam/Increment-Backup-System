package org.dam.common.utils.comgress;

import org.dam.common.utils.FileUtils;
import org.xerial.snappy.Snappy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
